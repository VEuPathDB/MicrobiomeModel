package org.apidb.apicommon.model.datasetInjector;

import org.apidb.apicommon.datasetPresenter.ModelReference;
import org.apidb.apicommon.datasetPresenter.DatasetInjector;

public class Microbiome16S extends DatasetInjector {


  @Override
  public void injectTemplates() {
      injectTemplate("sampleMetadataDatasetsParamQuery");
      injectTemplate("sampleMetadataQuestion");
  }

  @Override
  public void addModelReferences() {
      addWdkReference("SampleRecordClasses.MicrobiomeSampleRecordClass", "question",
                      "SampleQuestions.SamplesByMetadata_" + getDatasetName());
  }


  @Override
  public String[][] getPropertiesDeclaration() {

      String [][] declaration = {
          {"studyCategories", ""},
          {"projectAvailability", ""},
          {"studyAccess", ""},
          {"policyUrl", ""},
          {"cardHeadline", ""},
          {"cardPoints", ""},
          {"cardQuestions", ""},
      };

    return declaration;
  }

}
