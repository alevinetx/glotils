package self.alevinetx.plugins.maven.glo;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;


import java.util.HashMap;
import java.util.Map;

/**
 * Goal which creates a basic card in Glo
 *
 * 
 */
@Mojo(name = "add-basic-card", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class GloUtilMojo extends AbstractGloUtilMojo
{

    /**
     * Board ID
     */
    @Parameter(property = "boardId", required = true)
    private String boardId;

    /**
     * Column ID
     */
    @Parameter(property = "columnId", required = true)
    private String columnId;

    /**
     * Card title
     */
    @Parameter(property = "cardTitle", required = true)
    private String cardTitle;

    /**
     * Card description
     */
    @Parameter(property = "cardDesc", required = true)
    private String cardDesc;

    public void execute() throws MojoExecutionException, MojoFailureException
    {
        Map<String, Object> processResult = new HashMap<String, Object>();
        try
        {
            processResult =  getGloClient().addSimpleCard(boardId, columnId, cardTitle, cardDesc);
        }
        catch (Exception e)
        {
            throw new MojoExecutionException("Error adding a simple card", e);

        }

		if ( (Boolean)processResult.get(GloUtil.KEY_RESULT_PASSED).equals(Boolean.FALSE))
		{
			String respString = "Error adding a simple card:  ["  + 
								processResult.get(GloUtil.KEY_RESULT_CODE) +
								"]  " + processResult.get(GloUtil.KEY_RESULT_VALUE);
			throw new MojoFailureException(respString);
        }
                
        this.getLog().info("New card added.  ID: " + processResult.get(GloUtil.KEY_RESULT_VALUE));

    } // execute
}
