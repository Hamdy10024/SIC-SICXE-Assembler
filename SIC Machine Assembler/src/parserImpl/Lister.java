package parserImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.util.List;

import parserInterafces.ILister;
import parserInterafces.IStatement;

public class Lister implements ILister {

	private String listingString;
	private String temp;

	@Override
	public File generateFile(List<IStatement> statements, File fileName) throws IOException {

		for (int i = 0; i < statements.size(); i++) {
			IStatement item = statements.get(i);
			listingString += item.location();
			listingString += '\t';
			temp = item.Label();

			if (temp.length() < 8) {
				while (temp.length() < 8) {
					temp += ' ';
				}
			}
			listingString += temp;
			listingString += "\t\t ";
			temp = item.operation();

			while (temp.length() < 6) {
				temp += ' ';
			}

			listingString += temp;
			listingString += "  ";

			temp = item.operands();

			while (temp.length() < 18) {
				temp += ' ';

			}
			listingString += "/t/t ";
			temp = "";
			temp = item.objectCode();
			while (temp.length() < 6) {
				temp += ' ';
			}
			listingString += temp;
			listingString += "\t";
			listingString += item.error();

			listingString += '\n';
		}
		File result = new File("listing.txt");

		FileWriter write = new FileWriter(result);
		write.append(listingString);
		write.close();
		
		return result;
	}

}
