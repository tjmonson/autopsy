/*
 * Sample module in the public domain.  Feel free to use this as a template
 * for your modules.
 * 
 *  Contact: Brian Carrier [carrier <at> sleuthkit [dot] org]
 *
 *  This is free and unencumbered software released into the public domain.
 *  
 *  Anyone is free to copy, modify, publish, use, compile, sell, or
 *  distribute this software, either in source code form or as a compiled
 *  binary, for any purpose, commercial or non-commercial, and by any
 *  means.
 *  
 *  In jurisdictions that recognize copyright laws, the author or authors
 *  of this software dedicate any and all copyright interest in the
 *  software to the public domain. We make this dedication for the benefit
 *  of the public at large and to the detriment of our heirs and
 *  successors. We intend this dedication to be an overt act of
 *  relinquishment in perpetuity of all present and future rights to this
 *  software under copyright law.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *  IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 *  OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 *  ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE. 
 */
package org.sleuthkit.autopsy.examples;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.openide.util.Exceptions;
import org.sleuthkit.autopsy.casemodule.Case;
import org.sleuthkit.autopsy.coreutils.Logger;
import org.sleuthkit.autopsy.externalresults.ExternalResultsImporter;
import org.sleuthkit.autopsy.externalresults.ExternalResultsXML;
import org.sleuthkit.autopsy.ingest.DataSourceIngestModule;
import org.sleuthkit.autopsy.ingest.DataSourceIngestModuleProgress;
import org.sleuthkit.autopsy.ingest.IngestJobContext;
import org.sleuthkit.autopsy.ingest.IngestModuleReferenceCounter;
import org.sleuthkit.autopsy.ingest.IngestServices;
import org.sleuthkit.datamodel.BlackboardArtifact.ARTIFACT_TYPE;
import org.sleuthkit.datamodel.BlackboardAttribute.ATTRIBUTE_TYPE;
import org.sleuthkit.datamodel.Content;
import org.sleuthkit.datamodel.TskCoreException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Sample data source ingest module that doesn't do much. Demonstrates use of
 * utility classes: ExecUtils and the org.sleuthkit.autopsy.externalresults
 * package.
 */
public class SampleExecutableDataSourceIngestModule implements DataSourceIngestModule {

    private static final IngestModuleReferenceCounter refCounter = new IngestModuleReferenceCounter();
    private static final String moduleName = SampleExecutableIngestModuleFactory.getModuleName();
    private long jobId;
    private String outputDirPath;

    @Override
    public void startUp(IngestJobContext context) throws IngestModuleException {
        jobId = context.getJobId();
        if (refCounter.incrementAndGet(jobId) == 1) {
            // Create an output directory for this job.
            outputDirPath = Case.getCurrentCase().getModulesOutputDirAbsPath() + File.separator + moduleName; //NON-NLS
            File outputDir = new File(outputDirPath);
            if (outputDir.exists() == false) {
                outputDir.mkdirs();
            }
        }
    }

    @Override
    public ProcessResult process(Content dataSource, DataSourceIngestModuleProgress progressBar) {
        if (refCounter.get(jobId) == 1) {
            try {
                // There will be two tasks: data source analysis and import of 
                // the results of the analysis.
                progressBar.switchToDeterminate(2);

                // Do the analysis. The following commented out code could be 
                // used to run an executable. In this case the executable takes 
                // two command line arguments, the path to the data source to be 
                // analyzed and the path to a results file. The results file is 
                // an XML file (see org.sleuthkit.autopsy.externalresults.autopsy_external_results.xsd)
                // with instructions for the import of blackboard artifacts, 
                // derived files, and reports generated by the analysis. In this 
                // sample ingest module, the generation of the analysis results is
                // simulated. 
                String dataSourcePath = dataSource.getImage().getPaths()[0];
                String resultsFilePath = outputDirPath + File.separator + String.format("job_%d_results.xml", jobId);
//                ExecUtil executor = new ExecUtil();
//                executor.execute("some.exe", dataSourcePath, resultsFilePath);
                generateSimulatedResults(dataSourcePath, resultsFilePath);
                progressBar.progress(1);

                // Import the results of the analysis.
                ExternalResultsImporter.importResultsFromXML(dataSource, resultsFilePath);
                progressBar.progress(2);
            } catch (TskCoreException | ParserConfigurationException | TransformerException | IOException ex) {
                Logger logger = IngestServices.getInstance().getLogger(moduleName);
                logger.log(Level.SEVERE, "Failed to simulate analysis and results import", ex);  //NON-NLS
                return ProcessResult.ERROR;
            }
        }

        return ProcessResult.OK;
    }

    private void generateSimulatedResults(String dataSourcePath, String resultsFilePath) throws ParserConfigurationException, IOException, TransformerConfigurationException, TransformerException {
        List<String> derivedFilePaths = generateSimulatedDerivedFiles();
        String reportFilePath = generateSimulatedReport();
        generateSimulatedResultsFile(dataSourcePath, derivedFilePaths, reportFilePath, resultsFilePath);
    }

    private List<String> generateSimulatedDerivedFiles() throws IOException {
        List<String> filePaths = new ArrayList<>();
        String fileContents = "This is a simulated derived file.";
        for (int i = 0; i < 3; ++i) {
            String fileName = String.format("job_%d_derived_file_%d.txt", jobId, i);
            generateFile(fileName, fileContents.getBytes());
            filePaths.add(outputDirPath + File.separator + fileName);
        }
        return filePaths;
    }

    private String generateSimulatedReport() throws IOException {
        String reportFileName = String.format("job_%d_report.txt", jobId);
        String reportContents = "This is a simulated report.";
        generateFile(reportFileName, reportContents.getBytes());
        return outputDirPath + File.separator + reportFileName;
    }

    private void generateFile(String fileName, byte[] fileContents) throws IOException {
        String filePath = outputDirPath + File.separator + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileOutputStream fileStream = new FileOutputStream(file)) {
            fileStream.write(fileContents);
            fileStream.flush();
        }
    }

    private void generateSimulatedResultsFile(String dataSourcePath, List<String> derivedFilePaths, String reportPath, String resultsFilePath) throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
        // Create the XML DOM document and the root element.
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement(ExternalResultsXML.ROOT_ELEM.toString());
        doc.appendChild(rootElement);

        // Add an artifacts list element to the root element.
        Element artifactsListElement = doc.createElement(ExternalResultsXML.ARTIFACTS_LIST_ELEM.toString());
        rootElement.appendChild(artifactsListElement);

        // Add an artifact element to the artifacts list element. A standard 
        // artifact type is used as the required type attribute of this 
        // artifact element.
        Element artifactElement = doc.createElement(ExternalResultsXML.ARTIFACT_ELEM.toString());
        artifactElement.setAttribute(ExternalResultsXML.TYPE_ATTR.toString(), ARTIFACT_TYPE.TSK_INTERESTING_FILE_HIT.getLabel());
        artifactsListElement.appendChild(artifactElement);

        // Add an optional file element to the artifact element, and add a path 
        // element to the file element. This is how a source file for an 
        // artifact is usually specified. If an artifact element has no file
        // element, the data source is used as the default source file for the
        // artifact.
        Element fileElement = doc.createElement(ExternalResultsXML.SOURCE_FILE_ELEM.toString());
        artifactElement.appendChild(fileElement);
        Element pathElement = doc.createElement(ExternalResultsXML.PATH_ELEM.toString());
        pathElement.setTextContent(dataSourcePath);
        fileElement.appendChild(pathElement);

        // Add an artifact attribute element to the artifact element. A standard 
        // artifact attribute type is used as the required type XML attribute of 
        // the artifact attribute element.
        Element artifactAttrElement = doc.createElement(ExternalResultsXML.ATTRIBUTE_ELEM.toString());
        artifactAttrElement.setAttribute(ExternalResultsXML.TYPE_ATTR.toString(), ATTRIBUTE_TYPE.TSK_SET_NAME.getLabel());
        artifactElement.appendChild(artifactAttrElement);

        // Add the required value element to the artifact attribute element, 
        // with an optional type XML attribute of ExternalXML.VALUE_TYPE_TEXT, 
        // which is the default.        
        Element artifactAttributeValueElement = doc.createElement(ExternalResultsXML.VALUE_ELEM.toString());
        artifactAttributeValueElement.setTextContent("SampleInterestingFilesSet");
        artifactAttrElement.appendChild(artifactAttributeValueElement);

        // Add an optional source module element to the artifct attribute 
        // element.
        Element artifactAttrSourceElement = doc.createElement(ExternalResultsXML.SOURCE_MODULE_ELEM.toString());
        artifactAttrSourceElement.setTextContent(moduleName);
        artifactAttrElement.appendChild(artifactAttrSourceElement);

        // Add an artifact element with a user-defined type. No file element is 
        // added to the artifact element, so the data source will be used as the 
        // default for the source file.
        artifactElement = doc.createElement(ExternalResultsXML.ARTIFACT_ELEM.toString());
        artifactElement.setAttribute(ExternalResultsXML.TYPE_ATTR.toString(), "SampleArtifactType");
        artifactsListElement.appendChild(artifactElement);

        // Add artifact attribute elements with user-defined types to the 
        // artifact element, adding value elements of assorted types.
        for (int i = 0; i < 4; ++i) {
            artifactAttrElement = doc.createElement(ExternalResultsXML.ATTRIBUTE_ELEM.toString());
            artifactAttrElement.setAttribute(ExternalResultsXML.TYPE_ATTR.toString(), "SampleArtifactAttributeType");
            artifactElement.appendChild(artifactAttrElement);
            artifactAttributeValueElement = doc.createElement(ExternalResultsXML.VALUE_ELEM.toString());
            switch (i) {
                case 0:
                    artifactAttributeValueElement.setAttribute(ExternalResultsXML.TYPE_ATTR.toString(), ExternalResultsXML.VALUE_TYPE_TEXT.toString());
                    artifactAttributeValueElement.setTextContent("One");
                    break;
                case 1:
                    artifactAttributeValueElement.setAttribute(ExternalResultsXML.TYPE_ATTR.toString(), ExternalResultsXML.VALUE_TYPE_INT32.toString());
                    artifactAttributeValueElement.setTextContent("2");
                    break;
                case 2:
                    artifactAttributeValueElement.setAttribute(ExternalResultsXML.TYPE_ATTR.toString(), ExternalResultsXML.VALUE_TYPE_INT64.toString());
                    artifactAttributeValueElement.setTextContent("3");
                    break;
                case 3:
                    artifactAttributeValueElement.setAttribute(ExternalResultsXML.TYPE_ATTR.toString(), ExternalResultsXML.VALUE_TYPE_DOUBLE.toString());
                    artifactAttributeValueElement.setTextContent("4.0");
                    break;
            }
            artifactAttrElement.appendChild(artifactAttributeValueElement);
        }

        // Add a reports list element to the root element.
        Element reportsListElement = doc.createElement(ExternalResultsXML.REPORTS_LIST_ELEM.toString());
        rootElement.appendChild(reportsListElement);

        // Add a report element to the reports list element.
        Element reportElement = doc.createElement(ExternalResultsXML.REPORT_ELEM.toString());
        reportsListElement.appendChild(reportElement);

        // Add the required display name element to the report element.
        Element reportDisplayNameElement = doc.createElement(ExternalResultsXML.DISPLAY_NAME_ELEM.toString());
        reportDisplayNameElement.setTextContent("Sample Report");
        reportElement.appendChild(reportDisplayNameElement);

        // Add the required local path element to the report element.
        Element reportPathElement = doc.createElement(ExternalResultsXML.LOCAL_PATH_ELEM.toString());
        reportPathElement.setTextContent(reportPath);
        reportElement.appendChild(reportPathElement);

        // Add a derived files list element to the root element.
        Element derivedFilesListElement = doc.createElement(ExternalResultsXML.DERIVED_FILES_LIST_ELEM.toString());
        rootElement.appendChild(derivedFilesListElement);

        // Add derived file elements to the derived files list element. Each 
        // file element gets a required absolute path element and one gets an 
        // optional parent file path element. If the parent file path is not 
        // supplied, the data source root directory is used as the default 
        // parent.
        Element parentPathElement = null;
        for (String filePath : derivedFilePaths) {
            Element derivedFileElement = doc.createElement(ExternalResultsXML.DERIVED_FILE_ELEM.toString());
            derivedFilesListElement.appendChild(derivedFileElement);
            Element localPathElement = doc.createElement(ExternalResultsXML.LOCAL_PATH_ELEM.toString());
            localPathElement.setTextContent(filePath);
            derivedFileElement.appendChild(localPathElement);
            if (parentPathElement == null) {
                parentPathElement = doc.createElement(ExternalResultsXML.PARENT_PATH_ELEM.toString());
                parentPathElement.setTextContent(dataSourcePath);
                derivedFileElement.appendChild(parentPathElement);
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(resultsFilePath));
        transformer.transform(source, result);
    }
}
