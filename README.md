Implements Glo API, with a maven plugin wrapper

From:  https://support.gitkraken.com/developers/api/


currently requires having retrieved the board and column ID from a prior call to:  
  /boards -> get a board ID
  /boards/[board id]?fields=columns    ->  get column IDs for that board


Working Goals:

  add-basic-card : creates a very basic card for a given board/column, with only a title and description.

Next TBD:

 - get board ID by Name
 - get column ID by Name


Maven mojo wraps calls to  self.alevinetx.plugins.maven.glo.GloUtil, which can be used outside of maven.

Sample Call:

 mvn self.alevine.plugins.maven:glotils-maven-plugin:add-basic-card 

 -Dpat=[PAT from gitkraken.com]

 -DboardId=[board id]

 -DcolumnId=[column id]

 -DcardTitle="go maven!" 

 -DcardDesc="a very simple card, can be called from a failed Jenkins job"



