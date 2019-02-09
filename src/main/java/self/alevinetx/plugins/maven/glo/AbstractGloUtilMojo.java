package self.alevinetx.plugins.maven.glo;

import org.apache.maven.plugin.*;
import org.apache.maven.plugins.annotations.Parameter;

public abstract class AbstractGloUtilMojo extends AbstractMojo
{

    /**
     * Location of glo api
     */
    @Parameter(property = "apiUrl", required = false, defaultValue = "https://gloapi.gitkraken.com/v1/glo")
    private String apiUrl;

    /**
     * GLO PAT
     */
    @Parameter(property = "pat", required = true)
    private String pat;


    public GloUtil getGloClient()
    {
        return new GloUtil(pat, apiUrl);
    }
}