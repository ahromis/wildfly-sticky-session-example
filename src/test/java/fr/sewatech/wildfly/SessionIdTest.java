package fr.sewatech.wildfly;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(Arquillian.class)
public class SessionIdTest {

    public static final String WEBAPP_SRC = "src/main/webapp";

    @Deployment(testable = false)
    public static Archive deploy() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(SessionIdServlet.class)
                .addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/web.xml"));
    }

    @ArquillianResource
    URL root;

    @Test
    public void suffix_should_be_xxx() {
        try {

            URL servlet = new URL(root, "session");
            HttpURLConnection con = (HttpURLConnection) servlet.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                assertThat(readBody(con), endsWith(".xxx"));
            } else {
                fail("Response code : " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readBody(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

}
