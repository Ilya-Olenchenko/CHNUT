/*
 * @(#)Configuration.java	1.18 00/02/02
 *
 * Copyright 1997-2000 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.tools.doclets;

import com.sun.javadoc.*;
import java.util.*;
import java.io.*;

/**
 * Configure the output based on the options. Doclets should sub-class 
 * Configuration, to configure and add their own options. This class contains
 * all user options which are supported by the 1.1 doclet and the standard
 * doclet.
 *
 * @author Robert Field.
 * @author Atul Dambalkar.
 */
public abstract class Configuration {
    /**
     * The Root of the generated Program Structure from the Doclet API.
     */
    public static RootDoc root;

    /**
     * Destination directory name, in which doclet will generate the entire
     * documentation. Default is current directory.
     */
    public String destdirname = "";

    /**
     * Encoding for this document. Default is default encoding for this 
     * platform.
     */
    public String docencoding = null;

    /**
     * Encoding for this document. Default is default encoding for this 
     * platform.
     */ 
    public String encoding = null;

    /**
     * Generate author specific information for all the classes if @author 
     * tag is used in the doc comment and if -author option is used.
     * <code>showauthor</code> is set to true if -author option is used.
     * Default is don't show author information. 
     */
    public boolean showauthor = false;

    /**
     * Generate version specific information for the all the classes 
     * if @version tag is used in the doc comment and if -version option is 
     * used. <code>showversion</code> is set to true if -version option is 
     * used.Default is don't show version information. 
     */
    public boolean showversion = false;

    /**
     * Don't generate the date in the generated documentation, if -nodate 
     * option is used. <code>nodate</code> is set to true if -nodate 
     * option is used. Default is don't show date. 
     */
    public boolean nodate = false;

    /**
     * Sourcepath from where to read the source files. Default is classpath.
     * 
     */
    public String sourcepath = "";

    /**
     * Don't generate deprecated API information at all, if -nodeprecated 
     * option is used. <code>nodepracted</code> is set to true if 
     * -nodeprecated option is used. Default is generate deprected API 
     * information.
     */
    public boolean nodeprecated = false;

    /**
     * All the packages to be documented.
     */
    public PackageDoc[] packages;

    /**
     * Message Retriever for the doclet, to retrieve message from the resource
     * file for this Configuration, which is common for 1.1 and standard 
     * doclets.
     */
    public static MessageRetriever message = null;

    /**
     * This method should be defined in all those doclets(configurations), 
     * which want to derive themselves from this Configuration. This method
     * can be used to set its own command line options.
     * 
     * @param root Root of the Program Structure generated by Javadoc.
     */
    public abstract void setSpecificDocletOptions(RootDoc root) 
                                           throws DocletAbortException;

    /**
     * This method should be defined in all those doclets 
     * which want to inherit from this Configuration. This method 
     * should return the number of arguments to the command line option. 
     * This method is called by the method {@link #optionLength(String)}.
     * 
     * @param option Command line option under consideration.
     * @return number of arguments to option. Zero return means
     * option not known.  Negative value means error occurred.
     * @see #optionLength(String)  
     */
    public abstract int specificDocletOptionLength(String option);

    /**
     * This method should be defined in all those doclets 
     * which want to inherit from this Configuration. This method
     * can be used to check the validity of its own command line options. 
     * This method is called by {@link #validOptions(String[][], 
     * DocErrorReporter)}.
     * 
     * @param option   Options-array from the Javadoc.
     * @param reporter Error reporter.
     * @return True if all the options are valid else false.
     */
    public abstract boolean specificDocletValidOptions(String option[][],
                                                   DocErrorReporter reporter);

    /**
     * Constructor. Constructs the message retriever with resource file.
     */
    public Configuration() {
        if (message == null) {
            message =
                new MessageRetriever("com.sun.tools.javadoc.resources.doclets");
        }
    }

    /**
     * Set the command line options supported by this configuration.
     *
     * @param root Root of the Program Structure generated by this Javadoc run.
     */
    public void setOptions(RootDoc root) throws DocletAbortException {
        String[][] options = root.options();
        Configuration.root = root;
        packages = root.specifiedPackages();
        Arrays.sort(packages);
        for (int oi = 0; oi < options.length; ++oi) {
            String[] os = options[oi];
            String opt = os[0].toLowerCase();
            if (opt.equals("-d")) {
                destdirname = addTrailingFileSep(os[1]);
            } else  if (opt.equals("-docencoding")) {
                docencoding = os[1];
            } else  if (opt.equals("-encoding")) {
                encoding = os[1];
            } else  if (opt.equals("-author")) {
                showauthor = true;
            } else  if (opt.equals("-version")) {
                showversion = true;
            } else  if (opt.equals("-nodeprecated")) {
                nodeprecated = true;
            } else  if (opt.equals("-xnodate")) {
                nodate = true;
            } else  if (opt.equals("-sourcepath")) {
                sourcepath = os[1];
            } else if (opt.equals("-classpath") && 
                       sourcepath.length() == 0) {
                sourcepath = os[1];
            }
        }
        if (sourcepath.length() == 0) {
            sourcepath = System.getProperty("env.class.path");
        }
        if (docencoding == null) {
            docencoding = encoding;
        }

        HtmlDocWriter.configuration = this;
        setSpecificDocletOptions(root);
    }

    /**
     * Add a traliling file separator, if not found or strip off extra trailing
     * file separators if any.
     *
     * @param path Path under consideration.
     * @return String Properly constructed path string.
     */
    String addTrailingFileSep(String path) {
	String fs = System.getProperty("file.separator");
        String dblfs = fs + fs;
        int indexDblfs;
        while ((indexDblfs = path.indexOf(dblfs)) >= 0) {
            path = path.substring(0, indexDblfs) + 
                        path.substring(indexDblfs + fs.length());
        }
	if (!path.endsWith(fs))
	    path += fs;
	return path;
    }

   /**
    * Returns the "length" of a given option. If an option takes no
    * arguments, its length is one. If it takes one argument, it's
    * length is two, and so on. This method is called by JavaDoc to
    * parse the options it does not recognize. It then calls
    * {@link #validOptions(String[][], DocErrorReporter)} to validate them.
    * <b>Note:</b><br>
    * The options arrive as case-sensitive strings. For options that
    * are not case-sensitive, use toLowerCase() on the option string
    * before comparing it.
    * </blockquote>
    *
    * @return number of arguments + 1 for a option. Zero return means
    * option not known.  Negative value means error occurred.
    */
    public int optionLength(String option) {
        option = option.toLowerCase();
        if (option.equals("-version") ||
            option.equals("-nodeprecated") ||
            option.equals("-author") ||
            option.equals("-xnodate")) {
            return 1;
        } else if (option.equals("-docencoding") ||
                   option.equals("-encoding") ||
                   option.equals("-sourcepath") ||
                   option.equals("-d")) {
            return 2;
        } else {
            return specificDocletOptionLength(option);
        }
    }

    /**
     * After parsing the available options using {@link #optionLength(String)},
     * JavaDoc invokes this method with an array of options-arrays, where
     * the first item in any array is the option, and subsequent items in
     * that array are its arguments. So, if -print is an option that takes
     * no arguments, and -copies is an option that takes 1 argument, then
     * <pre>
     *     -print -copies 3
     * </pre>
     * produces an array of arrays that looks like:
     * <pre>
     *      option[0][0] = -print
     *      option[1][0] = -copies
     *      option[1][1] = 3
     * </pre>
     * (By convention, command line switches start with a "-", but
     * they don't have to.)
     * This method is not required to be written by sub-classes and will
     * default gracefully (to true) if absent.
     * <P>
     * Printing option related error messages (using the provided
     * DocErrorReporter) is the responsibility of this method.
     *
     * @param options  Options used on the command line.
     * @param reporter Error reporter to be used.
     * @return true if all the options are valid.
     */
    public boolean validOptions(String options[][], 
                                DocErrorReporter reporter) {
        boolean docencodingfound = false;
        String encoding = "";
        for (int oi = 0; oi < options.length; oi++) {
            String[] os = options[oi];
            String opt = os[0].toLowerCase();
            if (opt.equals("-d")) {
                destdirname = addTrailingFileSep(os[1]);
                File destDir = new File(destdirname);
                if (!destDir.exists()) {
                    reporter.printError(message.getText(
                        "doclet.destination_directory_not_found_0", 
                                                        destDir.getPath()));
                    return false;
                } else if (!destDir.isDirectory()) {
                    reporter.printError(message.getText(
                        "doclet.destination_directory_not_directory_0", 
                                                        destDir.getPath()));
                    return false;
                } else if (!destDir.canWrite()) {
                    reporter.printError(message.getText(
                        "doclet.destination_directory_not_writable_0", 
                                                        destDir.getPath()));
                    return false;
                }
            } else if (opt.equals("-docencoding")) {
                docencodingfound = true;
                if (!checkOutputFileEncoding(os[1], reporter)) {
                    return false;
                }
            } else if (opt.equals("-encoding")) {
                encoding = os[1];
            }
        }
        if (!docencodingfound && encoding.length() > 0) {
  	    if (!checkOutputFileEncoding(encoding, reporter)) {
                    return false;
            }
        }
        return specificDocletValidOptions(options, reporter);
    }

    /**
     * Check the validity of the given Source or Output File encoding on this 
     * platform. 
     *
     * @param docencoding Output file encoding.
     * @param reporter    Error reporter object.
     */
    protected boolean checkOutputFileEncoding(String docencoding,
                                              DocErrorReporter reporter) {
        OutputStream ost= new ByteArrayOutputStream();
        OutputStreamWriter osw = null;
   	try {
            osw = new OutputStreamWriter(ost, docencoding);
        } catch (UnsupportedEncodingException exc) {
            reporter.printError(message.getText(
                                "doclet.Encoding_not_supported", docencoding));
            return false;                  
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
            } catch (IOException exc) {
            }
        } 
        return true;
    }       

}


