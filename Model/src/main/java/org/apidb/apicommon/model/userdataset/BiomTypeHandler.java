package org.apidb.apicommon.model.userdataset;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.json.JSONObject;

import org.gusdb.wdk.model.WdkModelException;
import org.gusdb.wdk.model.user.dataset.UserDataset;
import org.gusdb.wdk.model.user.dataset.UserDatasetMeta;
import org.gusdb.wdk.model.user.dataset.UserDatasetCompatibility;
import org.gusdb.wdk.model.user.dataset.UserDatasetType;
import org.gusdb.wdk.model.user.dataset.UserDatasetTypeFactory;
import org.gusdb.wdk.model.user.dataset.UserDatasetTypeHandler;

public class BiomTypeHandler extends UserDatasetTypeHandler {

  private final static String NAME = "BIOM";
  private final static String VERSION = "1.0";
  private final static String DISPLAY = "BIOM";

  @Override
  public UserDatasetCompatibility getCompatibility(UserDataset userDataset, DataSource appDbDataSource) throws WdkModelException {
     return new UserDatasetCompatibility(true, new JSONObject(), "");
  }
  @Override
  public UserDatasetType getUserDatasetType() {
    return UserDatasetTypeFactory.getUserDatasetType(NAME, VERSION);
  }

  @Override
  public String getDisplay() {
        return DISPLAY;
  }

  @Override
  public String[] getInstallInAppDbCommand(UserDataset userDataset, Map<String, Path> fileNameToTempFileMap, String projectId) {
      Path biomFile = fileNameToTempFileMap.get("data.biom");
      if (biomFile == null) {
	  throw new RuntimeException("failed to get data.biom");
      }
      /*
       Wojtek: This is a hack. We want to show the summary on sample page, but I see no convenient way to query from the users database there
       Instead, pass the summary through, and store it in the ud_ProfileSet.name field
      */
      String summary;
      try {
          summary = userDataset.getMeta().getSummary();
      }
      catch(WdkModelException e) {
          throw new RuntimeException("Error getting dataset summary: " + e.toString());
      }
      String[] cmd = {"installBiomUserDataset", userDataset.getUserDatasetId().toString(), biomFile.toString(), projectId, summary};
      return cmd;
  }

  @Override
  public Set<String> getInstallInAppDbFileNames(UserDataset userDataset) {
      try {
          return userDataset.getFiles().keySet();
      }
      catch(WdkModelException e) {
          throw new RuntimeException("Error Getting all files for this dataset: " + e.toString());
      }
  }

  @Override
  public String[] getUninstallInAppDbCommand(Long userDatasetId, String projectId) {
    String[] cmd = {"uninstallBiomUserDataset", userDatasetId.toString(), projectId};
    return cmd;
  }

  @Override
  public String[] getRelevantQuestionNames(UserDataset userDataset) {
    String[] result = {"SampleQuestions.UserSampleByMetadata","SampleQuestions.UserSampleByTaxonAbundance"};
    return result;
  }

}
