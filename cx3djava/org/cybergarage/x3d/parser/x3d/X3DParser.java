/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 2002
*
*	File : X3DHandler.java
*
******************************************************************/

package org.cybergarage.x3d.parser.x3d;

import java.io.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class X3DParser extends org.cybergarage.x3d.parser.Parser
{
	public X3DParser()
	{
	}

    /** Namespaces feature id (http://xml.org/sax/features/namespaces). */
    protected static final String NAMESPACES_FEATURE_ID = "http://xml.org/sax/features/namespaces";

    /** Namespace prefixes feature id (http://xml.org/sax/features/namespace-prefixes). */
    protected static final String NAMESPACE_PREFIXES_FEATURE_ID = "http://xml.org/sax/features/namespace-prefixes";

    /** Validation feature id (http://xml.org/sax/features/validation). */
    protected static final String VALIDATION_FEATURE_ID = "http://xml.org/sax/features/validation";

    /** Schema validation feature id (http://apache.org/xml/features/validation/schema). */
    protected static final String SCHEMA_VALIDATION_FEATURE_ID = "http://apache.org/xml/features/validation/schema";

    /** Schema full checking feature id (http://apache.org/xml/features/validation/schema-full-checking). */
    protected static final String SCHEMA_FULL_CHECKING_FEATURE_ID = "http://apache.org/xml/features/validation/schema-full-checking";

    /** Dynamic validation feature id (http://apache.org/xml/features/validation/dynamic). */
    protected static final String DYNAMIC_VALIDATION_FEATURE_ID = "http://apache.org/xml/features/validation/dynamic";

    // default settings

    /** Default parser name. */
    protected static final String DEFAULT_PARSER_NAME = "org.apache.xerces.parsers.SAXParser";
    //protected static final String DEFAULT_PARSER_NAME = "javax.xml.parsers.SAXParser";

    /** Default repetition (1). */
    protected static final int DEFAULT_REPETITION = 1;

    /** Default namespaces support (true). */
    protected static final boolean DEFAULT_NAMESPACES = true;

    /** Default namespace prefixes (false). */
    protected static final boolean DEFAULT_NAMESPACE_PREFIXES = false;

    /** Default validation support (false). */
    protected static final boolean DEFAULT_VALIDATION = false;

    /** Default Schema validation support (false). */
    protected static final boolean DEFAULT_SCHEMA_VALIDATION = false;

    /** Default Schema full checking support (false). */
    protected static final boolean DEFAULT_SCHEMA_FULL_CHECKING = false;

    /** Default dynamic validation support (false). */
    protected static final boolean DEFAULT_DYNAMIC_VALIDATION = false;

    /** Default memory usage report (false). */
    protected static final boolean DEFAULT_MEMORY_USAGE = false;

    /** Default "tagginess" report (false). */
    protected static final boolean DEFAULT_TAGGINESS = false;

    /** Number of elements. */
    protected long fElements;

    /** Number of attributes. */
    protected long fAttributes;

    /** Number of characters. */
    protected long fCharacters;

    /** Number of ignorable whitespace characters. */
    protected long fIgnorableWhitespace;

    /** Number of characters of tags. */
    protected long fTagCharacters;

    /** Number of other content characters for the "tagginess" calculation. */
    protected long fOtherCharacters;


	public boolean parse(Reader reader) {

        // variables
        X3DHandler counter = new X3DHandler(this);
        PrintWriter out = new PrintWriter(System.out);
        XMLReader parser = null;
        int repetition = DEFAULT_REPETITION;
        boolean namespaces = DEFAULT_NAMESPACES;
        boolean namespacePrefixes = DEFAULT_NAMESPACE_PREFIXES;
        boolean validation = DEFAULT_VALIDATION;
        boolean schemaValidation = DEFAULT_SCHEMA_VALIDATION;
        boolean schemaFullChecking = DEFAULT_SCHEMA_FULL_CHECKING;
        boolean dynamicValidation = DEFAULT_DYNAMIC_VALIDATION;
        boolean memoryUsage = DEFAULT_MEMORY_USAGE;
        boolean tagginess = DEFAULT_TAGGINESS;

	// create parser
	try {
		parser = XMLReaderFactory.createXMLReader();
      }
	catch (Exception e) {
		parser = null;
	}

	// use default parser?
	if (parser == null) {
		// create parser
		try {
			parser = XMLReaderFactory.createXMLReader(DEFAULT_PARSER_NAME);
		}
		catch (Exception e) {
			System.err.println("error: Unable to instantiate parser ("+DEFAULT_PARSER_NAME+")");
		}
	}

	try {
                parser.setFeature(NAMESPACES_FEATURE_ID, namespaces);
                parser.setFeature(NAMESPACE_PREFIXES_FEATURE_ID, namespacePrefixes);
                parser.setFeature(VALIDATION_FEATURE_ID, validation);
                parser.setFeature(SCHEMA_VALIDATION_FEATURE_ID, schemaValidation);
                parser.setFeature(SCHEMA_FULL_CHECKING_FEATURE_ID, schemaFullChecking);
                parser.setFeature(DYNAMIC_VALIDATION_FEATURE_ID, dynamicValidation);
	} catch (SAXNotSupportedException e) {
	} catch (SAXNotRecognizedException e) {
	}

	// parse file
	parser.setContentHandler(counter);
	parser.setErrorHandler(counter);
	try {
		InputSource input = new InputSource(reader);
		parser.parse(input);
	}
	catch (SAXException e) {
	}
	catch (IOException e) {
	}

		return true;
		
	} // main(String[])

    //
    // Private static methods
    //

    /** Prints the usage. */
    private static void printUsage() {

        System.err.println("usage: java sax.X3DHandler (options) uri ...");
        System.err.println();

        System.err.println("options:");
        System.err.println("  -p name     Select parser by name.");
        System.err.println("  -x number   Select number of repetitions.");
        System.err.println("  -n  | -N    Turn on/off namespace processing.");
        System.err.println("  -np | -NP   Turn on/off namespace prefixes.");
        System.err.println("              NOTE: Requires use of -n.");
        System.err.println("  -v  | -V    Turn on/off validation.");
        System.err.println("  -s  | -S    Turn on/off Schema validation support.");
        System.err.println("              NOTE: Not supported by all parsers.");
        System.err.println("  -f  | -F    Turn on/off Schema full checking.");
        System.err.println("              NOTE: Requires use of -s and not supported by all parsers.");
        System.err.println("  -dv | -DV   Turn on/off dynamic validation.");
        System.err.println("              NOTE: Requires use of -v and not supported by all parsers.");
        System.err.println("  -m  | -M    Turn on/off memory usage report");
        System.err.println("  -t  | -T    Turn on/off \"tagginess\" report.");
        System.err.println("  --rem text  Output user defined comment before next parse.");
        System.err.println("  -h          This help screen.");

        System.err.println();
        System.err.println("defaults:");
        System.err.println("  Parser:     "+DEFAULT_PARSER_NAME);
        System.err.println("  Repetition: "+DEFAULT_REPETITION);
        System.err.print("  Namespaces: ");
        System.err.println(DEFAULT_NAMESPACES ? "on" : "off");
        System.err.print("  Prefixes:   ");
        System.err.println(DEFAULT_NAMESPACE_PREFIXES ? "on" : "off");
        System.err.print("  Validation: ");
        System.err.println(DEFAULT_VALIDATION ? "on" : "off");
        System.err.print("  Schema:     ");
        System.err.println(DEFAULT_SCHEMA_VALIDATION ? "on" : "off");
        System.err.print("  Schema full checking:     ");
        System.err.println(DEFAULT_SCHEMA_FULL_CHECKING ? "on" : "off");
        System.err.print("  Dynamic:    ");
        System.err.println(DEFAULT_DYNAMIC_VALIDATION ? "on" : "off");
        System.err.print("  Memory:     ");
        System.err.println(DEFAULT_MEMORY_USAGE ? "on" : "off");
        System.err.print("  Tagginess:  ");
        System.err.println(DEFAULT_TAGGINESS ? "on" : "off");

        System.err.println();
        System.err.println("notes:");
        System.err.println("  The speed and memory results from this program should NOT be used as the");
        System.err.println("  basis of parser performance comparison! Real analytical methods should be");
        System.err.println("  used. For better results, perform multiple document parses within the same");
        System.err.println("  virtual machine to remove class loading from parse time and memory usage.");
        System.err.println();
        System.err.println("  The \"tagginess\" measurement gives a rough estimate of the percentage of");
        System.err.println("  markup versus content in the XML document. The percent tagginess of a ");
        System.err.println("  document is equal to the minimum amount of tag characters required for ");
        System.err.println("  elements, attributes, and processing instructions divided by the total");
        System.err.println("  amount of characters (characters, ignorable whitespace, and tag characters)");
        System.err.println("  in the document.");
        System.err.println();
        System.err.println("  Not all features are supported by different parsers.");

    } // printUsage()
}

