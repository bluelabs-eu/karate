package com.intuit.karate;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pthomas3
 */
public class FileUtilsTest {
    
    private static final Logger logger = LoggerFactory.getLogger(FileUtilsTest.class);
    
    @Test
    public void testClassLoading() throws Exception {
        ClassLoader cl = FileUtils.createClassLoader("src/main/java/com/intuit/karate");
        InputStream is = cl.getResourceAsStream("StepDefs.java");
        String s = FileUtils.toString(is);
        assertTrue(s.trim().startsWith("/*"));
    }

    @Test
    public void testExtractingFeaturePathFromCommandLine() {
        String expected = "/Users/pthomas3/dev/zcode/karate/karate-junit4/src/test/java/com/intuit/karate/junit4/demos/users.feature";
        String cwd = "/Users/pthomas3/dev/zcode/karate/karate-junit4";
        String intelllij = "com.intellij.rt.execution.application.AppMain cucumber.api.cli.Main --plugin org.jetbrains.plugins.cucumber.java.run.CucumberJvmSMFormatter --monochrome --name ^get users and then get first by id$ --glue com.intuit.karate /Users/pthomas3/dev/zcode/karate/karate-junit4/src/test/java/com/intuit/karate/junit4/demos/users.feature";
        String path = FileUtils.getFeaturePath(intelllij, cwd);
        assertEquals(expected, path);
        String eclipse = "com.intuit.karate.StepDefs - cucumber.api.cli.Main /Users/pthomas3/dev/zcode/karate/karate-junit4/src/test/java/com/intuit/karate/junit4/demos/users.feature --glue classpath: --plugin pretty --monochrome";
        path = FileUtils.getFeaturePath(eclipse, cwd);
        assertEquals(expected, path);
    }
    
    @Test
    public void testWindowsFileNames() {
    	String path = "com/intuit/karate/cucumber/scenario.feature";
    	String fixed = FileUtils.toPackageQualifiedName(path);
    	assertEquals("com.intuit.karate.cucumber.scenario", fixed);
    }
    
    @Test
    public void testRenameZeroLengthFile() {
        long time = System.currentTimeMillis();
        String name = "target/" + time + ".json";
        FileUtils.writeToFile(new File(name), "");
        FileUtils.renameFileIfZeroBytes(name);
        File file = new File(name + ".fail");
        assertTrue(file.exists());
    }
    
    @Test
    public void testScanFiles() {
        String relativePath = "classpath:com/intuit/karate/ui/test.feature";
        List<FileResource> files = FileUtils.scanForFeatureFilesOnClassPath();
        boolean found = false;
        for (FileResource file : files) {
            if (file.relativePath.equals(relativePath)) {
                File tempFile = FileUtils.fromRelativeClassPath(relativePath);                
                assertEquals(tempFile, file.file);
                String temp = FileUtils.toRelativeClassPath(file.file);
                assertEquals(temp, file.relativePath);
                found = true;
                break;
            }
        }
        assertTrue(found);
    }
    
    @Test
    public void testRelativePathForClass() {
        assertEquals("classpath:com/intuit/karate", FileUtils.toRelativeClassPath(getClass()));
    }
    
}
