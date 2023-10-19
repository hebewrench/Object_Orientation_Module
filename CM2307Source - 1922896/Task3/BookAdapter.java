public class BookAdapter extends Book{
  IncompatibleBook incompBook = new IncompatibleBook();
  public void setTitleString(String aString) {incompBook.setTitle(aString);}
  public String getTitleString(){
    return incompBook.getTitle() ;}
}
