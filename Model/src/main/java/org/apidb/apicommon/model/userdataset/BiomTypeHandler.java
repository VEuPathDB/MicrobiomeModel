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
  /* Can't actually handle all of those BIOM files
  *  Only does 1.0 sparse files, with data points in the tsv file
  *  This was done because there is no good way to add dependencies to the installer script
  */
  private final static String VERSION = "1.0, 2.0, or 2.1";
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
      Path metadataJson = fileNameToTempFileMap.get("metadata.json");
      if (metadataJson == null) {
        throw new RuntimeException("Failed to get: metadata.json");
      }
      Path dataTsv = fileNameToTempFileMap.get("data.tsv");
      String dataTsvOrEmptyStringMeaningThingsToTheInstaller = dataTsv == null ? "" : dataTsv.toString();

      String[] cmd = {
          "installBiomUserDataset",
          userDataset.getUserDatasetId().toString(),
          metadataJson.toString(),
          dataTsvOrEmptyStringMeaningThingsToTheInstaller,
          projectId
      };
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
