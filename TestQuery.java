package testing;

import util.FormatQuery;
import util.FormatQuery.SearchOperator;
public class TestQuery {

	public static FormatQuery frmQ;
	public TestQuery() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] keywords = { "Terms You Need to Know in order to talk",
				"We are super excited to announce our first winner",
				"We are thinking of putting together the first leg of our research paper" };
		String []neg = {"super excited"};
		frmQ = new FormatQuery(keywords,neg);
		frmQ.makeQuery(SearchOperator.Operator_OR);
		if(!FormatQuery.IS_Exception)
		{
			System.out.println(FormatQuery.finalQuery);
		}
	}

}
