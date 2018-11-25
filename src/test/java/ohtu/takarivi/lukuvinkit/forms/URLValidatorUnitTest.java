package ohtu.takarivi.lukuvinkit.forms;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class URLValidatorUnitTest {
    @Test
    public void acceptsValidURL() {
        String url = "https://example.com/";
        assertTrue(LinkAddForm.isValidURL(url)&&VideoAddForm.isValidURL(url));
    }

    @Test
    public void rejectsURLWithoutProtocol() {
        String url = "://example.com/";
        assertFalse(LinkAddForm.isValidURL("://example.com/")&&VideoAddForm.isValidURL(url));
    }

    @Test
    public void rejectsURLWithWhitespace() {
        String url = "https://example.com/           .txt";
        assertFalse(LinkAddForm.isValidURL(url)&&VideoAddForm.isValidURL(url));
    }

    @Test
    public void rejectsURLWithInvalidDomain() {
        String url = "https://<b>example.com/";
        assertFalse(LinkAddForm.isValidURL(url)&&VideoAddForm.isValidURL(url));
    }
}