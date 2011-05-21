package demo.codeanalyzer.common.model;


/**
 * Stores location information of main elements of java class
 *
 * @author Deepa Sobhana, Seema Richard
 */
public class LocationInfo implements Location
{

  private int startOffset;
  private int startRelativeOffset;
  private int endOffset;
  private int endRelativeOffset;
  private long startLineNumber;
  private long stopLineNumber;

  public int getEndOffset()
  {
    return endOffset;
  }

  public void setEndOffset(int endOffset)
  {
    this.endOffset = endOffset;
  }

  public int getStartOffset()
  {
    return startOffset;
  }

  public void setStartOffset(int startOffset)
  {
    this.startOffset = startOffset;
  }

  public long getStartLineNumber()
  {
    return startLineNumber;
  }

  public void setStartLineNumber(long lineNumber)
  {
    this.startLineNumber = lineNumber;
  }

  public long getStopLineNumber()
  {
    return stopLineNumber;
  }

  public void setStopLineNumber(long lineNumber)
  {
    this.stopLineNumber = lineNumber;
  }

  public int getEndRelativeOffset()
  {
    return endRelativeOffset;
  }

  public void setStopColumn(int lineNumber)
  {
    this.endRelativeOffset = lineNumber;
  }

  public int getStartRelativeOffset()
  {
    return startRelativeOffset;
  }

  public void setStartColumn(int lineNumber)
  {
    this.startRelativeOffset = lineNumber;
  }
}
