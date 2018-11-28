package org.apidb.apicommon.model.datasetInjector;

import org.apidb.apicommon.datasetPresenter.ModelReference;
import org.apidb.apicommon.datasetPresenter.DatasetInjector;

public class Microbiome16S extends DatasetInjector {


  @Override
  public void injectTemplates() {
  }

  @Override
  public void addModelReferences() {
  }


  @Override
  public String[][] getPropertiesDeclaration() {

      String [][] declaration = {
          //                                 {"isPublic", ""},
      };

    return declaration;
  }

}
