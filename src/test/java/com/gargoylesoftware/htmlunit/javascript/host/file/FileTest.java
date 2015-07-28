/*
 * Copyright (c) 2002-2015 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.htmlunit.javascript.host.file;

import static com.gargoylesoftware.htmlunit.BrowserRunner.Browser.CHROME;
import static com.gargoylesoftware.htmlunit.BrowserRunner.Browser.FF;
import static com.gargoylesoftware.htmlunit.BrowserRunner.Browser.IE11;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.gargoylesoftware.htmlunit.BrowserRunner;
import com.gargoylesoftware.htmlunit.BrowserRunner.Alerts;
import com.gargoylesoftware.htmlunit.BrowserRunner.NotYetImplemented;
import com.gargoylesoftware.htmlunit.WebDriverTestCase;
import com.gargoylesoftware.htmlunit.html.HtmlPageTest;

/**
 * Tests for {@link File}.
 *
 * @version $Revision$
 * @author Ronald Brill
 */
@RunWith(BrowserRunner.class)
public class FileTest extends WebDriverTestCase {

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(CHROME = { "1", "ScriptExceptionTest1.txt",
                            "Sun Jul 26 2015 16:21:47 GMT+0200 (Central European Summer Time)",
                            "1437920507152", "", "291", "text/plain" },
            FF31 = { "1", "ScriptExceptionTest1.txt", "Sun Jul 26 2015 16:21:47 GMT+0200",
                            "undefined", "undefined", "291", "text/plain" },
            FF38 = { "1", "ScriptExceptionTest1.txt", "Sun Jul 26 2015 16:21:47 GMT+0200",
                            "1437920507152", "undefined", "291", "text/plain" },
            IE11 = { "1", "ScriptExceptionTest1.txt",
                            "Sun Jul 26 2015 16:21:47 GMT+0200 (Central European Summer Time)",
                            "undefined", "undefined", "291", "text/plain" },
            IE8 = { "" })
    @NotYetImplemented({ FF, CHROME, IE11 })
    public void properties() throws Exception {
        final String html
            = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html>\n"
            + "<head><title>foo</title>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  if (document.testForm.fileupload.files) {\n"
            + "    var files = document.testForm.fileupload.files;\n"
            + "    alert(files.length);\n"

            + "    var file = files[0];\n"
            + "    alert(file.name);\n"
            + "    alert(file.lastModifiedDate);\n"
            + "    alert(file.lastModified);\n"
            + "    alert(file.webkitRelativePath);\n"
            + "    alert(file.size);\n"
            + "    alert(file.type);\n"
            + "  }\n"
            + "}\n"
            + "</script>\n"
            + "</head>\n"
            + "<body>\n"
            + "  <form name='testForm'>\n"
            + "    <input type='file' id='fileupload' name='fileupload'>\n"
            + "  </form>\n"
            + "  <button id='testBtn' onclick='test()'>Tester</button>\n"
            + "</body>\n"
            + "</html>";

        final WebDriver driver = loadPage2(html);

        String url = getClass().getClassLoader().getResource("ScriptExceptionTest1.txt").toExternalForm();
        url = url.substring(6);
        url = url.replace('/', java.io.File.separatorChar);

        driver.findElement(By.id("fileupload")).sendKeys(url);

        driver.findElement(By.id("testBtn")).click();
        assertEquals(getExpectedAlerts(), getCollectedAlerts(driver));
    }

}