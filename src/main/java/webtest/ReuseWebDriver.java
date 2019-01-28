package webtest;

import static org.openqa.selenium.remote.CapabilityType.SUPPORTS_JAVASCRIPT;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.net.UrlChecker;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.internal.WebElementToJsonConverter;

/**
 * ResumeWebdriver
 *
 */
public class ReuseWebDriver extends RemoteWebDriver {

    private Capabilities mycapabilities;

    private String serverUrl;

    public ReuseWebDriver(String serverUrl, String sessionID) throws MalformedURLException {
        super(new myHttpCommandExecutor(new URL(serverUrl)), null);
        this.serverUrl = serverUrl;
        myStartSession(sessionID);
    }

    @Override
    protected void startSession(Capabilities capabilities) {
        // Do Nothing
    }

    public Capabilities getCapabilities() {
        return mycapabilities;
    }

    public boolean isJavascriptEnabled() {
        return mycapabilities.is(SUPPORTS_JAVASCRIPT);
    }

    public Object executeScript(String script, Object... args) {
        if (!isJavascriptEnabled()) {
            throw new UnsupportedOperationException(
                    "You must be using an underlying instance of WebDriver that supports executing javascript");
        }

        // Escape the quote marks
        script = script.replaceAll("\"", "\\\"");

        List<Object> convertedArgs = Stream.of(args).map(new WebElementToJsonConverter()).collect(Collectors.toList());

        Map<String, ?> params = ImmutableMap.of("script", script, "args", convertedArgs);

        return execute(DriverCommand.EXECUTE_SCRIPT, params).getValue();
    }

    @Override
    public Object executeAsyncScript(String script, Object... args) {
        if (!isJavascriptEnabled()) {
            throw new UnsupportedOperationException(
                    "You must be using an underlying instance of " + "WebDriver that supports executing javascript");
        }

        // Escape the quote marks
        script = script.replaceAll("\"", "\\\"");

        List<Object> convertedArgs = Stream.of(args).map(new WebElementToJsonConverter()).collect(Collectors.toList());

        Map<String, ?> params = ImmutableMap.of("script", script, "args", convertedArgs);

        return execute(DriverCommand.EXECUTE_ASYNC_SCRIPT, params).getValue();
    }

    protected void myStartSession(String sessionID) {
        if (!sessionID.isEmpty()) {
            super.setSessionId(sessionID);

        }
        Command command = new Command(super.getSessionId(), DriverCommand.STATUS);
        Response response;
        try {
            response = getCommandExecutor().execute(command);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't use this Session");
            return;
        }

        this.mycapabilities = new MutableCapabilities();

        if (response.getValue() instanceof Exception) {
            ((Exception) response.getValue()).printStackTrace();
        }

    }

    public void quit() {
        super.quit();

        // 以下逻辑用于关闭DriverService，避免无法关闭DriverService，导致出现过多Driver进程。
        WebDriverException toThrow = null;
        try {
            URL killUrl = new URL(serverUrl + "/shutdown");
            new UrlChecker().waitUntilUnavailable(3, TimeUnit.SECONDS, killUrl);
        } catch (MalformedURLException e) {
            toThrow = new WebDriverException(e);
        } catch (UrlChecker.TimeoutException e) {
            toThrow = new WebDriverException("Timed out waiting for driver server to shutdown.", e);
        }

        if (toThrow != null) {
            throw toThrow;
        }
    }

}
