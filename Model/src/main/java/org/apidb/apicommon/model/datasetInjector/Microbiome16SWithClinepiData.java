package org.apidb.apicommon.model.datasetInjector;

import org.apidb.apicommon.datasetPresenter.DatasetInjector;

public class Microbiome16SWithClinepiData extends DatasetInjector {

  @Override
  public void injectTemplates() {
      injectTemplate("sampleMetadataDatasetsParamQuery");

      injectTemplate("sampleMetadataQuestionWithClinepi");
      injectTemplate("sampleMetadataQueryWithClinepi");
      injectTemplate("sampleMetadataParamsWithClinepi");
      injectTemplate("sampleMetadataParamQueriessWithClinepi");

      
      injectTemplate("sampleMetadataQuestionCategory");

      injectTemplate("taxonAbundanceQuestion");
      injectTemplate("taxonAbundanceQuestionCategory");

  }

  @Override
  public void addModelReferences() {
      addWdkReference("SampleRecordClasses.MicrobiomeSampleRecordClass", "question",
                      "SampleQuestions.SamplesByMetadata_" + getDatasetName());

      addWdkReference("SampleRecordClasses.MicrobiomeSampleRecordClass", "question",
                      "SampleQuestions.SamplesByTaxonAbundance_" + getDatasetName());
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
          {"clinepiTablePrefix", ""},
      };

    return declaration;
  }

}

