package com.sabre.hotelbooker.pageobjects;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import java.util.List;

public class PageObjectGenerator {
        public static void main(String[] args) {
            // Minimal Playwright setup for demonstration
            try (com.microsoft.playwright.Playwright playwright = com.microsoft.playwright.Playwright.create()) {
                com.microsoft.playwright.Browser browser = playwright.chromium().launch();
                com.microsoft.playwright.Page page = browser.newPage();
                // Replace with your actual login page URL
                String url = "https://hotelbooker.cert.sabre.com";
                page.navigate(url);
                // Generate and print the LoginPage class code
                generatePageObject(page, "LoginPage");
                browser.close();
            }
        }
    public static void generatePageObject(Page page, String className) {
        List<ElementHandle> elements = page.querySelectorAll("input,button");
        StringBuilder sb = new StringBuilder();
        sb.append("package com.sabre.hotelbooker.pageobjects;\n\n");
        sb.append("import com.microsoft.playwright.Page;\n\n");
        sb.append("public class ").append(className).append(" {\n");
        sb.append("    private Page page;\n\n");
        sb.append("    public ").append(className).append("(Page page){\n");
        sb.append("        this.page = page;\n");
        sb.append("    }\n\n");
        for (ElementHandle el : elements) {
            String id = el.getAttribute("id");
            String name = el.getAttribute("name");
            String type = el.getAttribute("type");
            String selector = id != null && !id.isEmpty() ? "#" + id : (name != null && !name.isEmpty() ? "[name='" + name + "']" : null);
            if (selector == null) continue;
            String fieldName = (id != null && !id.isEmpty()) ? id : (name != null && !name.isEmpty() ? name : "element");
            sb.append("    // type: ").append(type).append("\n");
            sb.append("    private final String ").append(fieldName).append(" = \"").append(selector).append("\";\n");
            if ("button".equals(type)) {
                sb.append("    public void click").append(capitalize(fieldName)).append("() {\n");
                sb.append("        page.click(").append(fieldName).append(");\n");
                sb.append("    }\n\n");
            } else {
                sb.append("    public void set").append(capitalize(fieldName)).append("(String value) {\n");
                sb.append("        page.fill(").append(fieldName).append(", value);\n");
                sb.append("    }\n\n");
            }
        }
        sb.append("}\n");
        System.out.println(sb.toString());
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
