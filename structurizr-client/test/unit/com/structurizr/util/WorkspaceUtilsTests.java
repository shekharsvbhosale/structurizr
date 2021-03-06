package com.structurizr.util;

import com.structurizr.Workspace;
import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class WorkspaceUtilsTests {

    @Test
    public void test_loadWorkspaceFromJson_ThrowsAnException_WhenANullFileIsSpecified() {
        try {
            WorkspaceUtils.loadWorkspaceFromJson(null);
            fail();
        } catch (Exception e) {
            assertEquals("The path to a JSON file must be specified.", e.getMessage());
        }
    }

    @Test
    public void test_loadWorkspaceFromJson_ThrowsAnException_WhenTheFileDoesNotExist() {
        try {
            WorkspaceUtils.loadWorkspaceFromJson(new File("test/unit/com/structurizr/util/other-workspace.json"));
            fail();
        } catch (Exception e) {
            assertEquals("The specified JSON file does not exist.", e.getMessage());
        }
    }

    @Test
    public void test_saveWorkspaceToJson_ThrowsAnException_WhenANullWorkspaceIsSpecified() {
        try {
            WorkspaceUtils.saveWorkspaceToJson(null, null);
            fail();
        } catch (Exception e) {
            assertEquals("A workspace must be provided.", e.getMessage());
        }
    }

    @Test
    public void test_saveWorkspaceToJson_ThrowsAnException_WhenANullFileIsSpecified() {
        try {
            WorkspaceUtils.saveWorkspaceToJson(new Workspace("Name", "Description"), null);
            fail();
        } catch (Exception e) {
            assertEquals("The path to a JSON file must be specified.", e.getMessage());
        }
    }

    @Test
    public void test_saveWorkspaceToJson_and_loadWorkspaceFromJson() throws Exception {
        File file = new File("build/workspace-utils.json");
        Workspace workspace = new Workspace("Name", "Description");
        WorkspaceUtils.saveWorkspaceToJson(workspace, file);

        workspace = WorkspaceUtils.loadWorkspaceFromJson(file);
        assertEquals("Name", workspace.getName());
    }

    @Test
    public void test_loadWorkspaceFromJson_WorksWithSomeWorkspaceExamples() throws Exception {
        File[] files = new File("test/integration/workspaces").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".json");
            }
        });

        assertTrue(files != null && files.length > 0);

        for (File file : files) {
            WorkspaceUtils.loadWorkspaceFromJson(file);
        }
    }

}