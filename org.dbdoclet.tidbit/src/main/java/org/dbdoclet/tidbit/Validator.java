/* 
 * $Id$
 *
 * ### Copyright (C) 2006 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.tidbit;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.application.Application;
import org.dbdoclet.tidbit.common.Context;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.project.Project;

public class Validator {

    private String cause;
    private Context ctx;
    private ResourceBundle res;
    private final Project project;
    
    public Validator(Application application, String cause) {

        if (application == null) {
            throw new IllegalArgumentException("The argument application must not be null!");
        }

        if (cause == null) {
            throw new IllegalArgumentException("The argument cause must not be null!");
        }

        this.cause = cause;

        project = application.getProject();
        ctx = application.getContext();
            
        res = ctx.getResourceBundle();
    }

    public boolean check() throws IOException {

        if (cause.equals("tab.changed") || cause.equals("jdk.changed") || cause.equals("project-file.changed")
                || cause.equals("project-directory.changed") || cause.equals("destination-directory.changed")) {

            if (checkData() == true) {
                return true;
            } else {
                return false;
            }
        }

        if (cause.equals("save")) {

            if (checkSave() == true) {
                return true;
            } else {
                return false;
            }
        }

        return true;
    }

    private boolean checkData() throws IOException {

        String text = project.getDestinationPath();
        
        if (text == null || text.length() == 0) {
            return false;

        }

        File dir = new File(text);

        if (dir.exists() == false) {
            FileServices.createPath(dir);
        }

        if (dir.exists() == false || dir.isDirectory() == false) {

            String msg = MessageFormat.format(ResourceServices.getString(res,"C_ERROR_ENTRY_IS_NOT_A_DIRECTORY"), text);
            ErrorBox.show(ResourceServices.getString(res,"C_ERROR"), msg);
            return false;
        }

        return true;
    }

    private boolean checkSave() throws IOException {

        String projectName = project.getProjectName();

        if ((projectName == null) || (projectName.length() == 0)) {

            ErrorBox.show(StaticContext.getDialogOwner(), ResourceServices.getString(res,"C_ERROR"), res
                    .getString("C_ERROR_PROJECT_NAME_MUST_BE_SET"));

            return false;
        }

        File projectFile = project.getProjectFile();

        if (projectFile == null) {

            ErrorBox.show(StaticContext.getDialogOwner(), ResourceServices.getString(res,"C_ERROR"), res
                    .getString("C_ERROR_PROJECT_FILE_MUST_BE_SET"));

            return false;
        }

//        File docBookFile = project.getFileManager().getDocBookFile();
//
//        if (docBookFile == null) {
//
//            ErrorBox.show(StaticContext.getDialogOwner(), ResourceServices.getString(res,"C_ERROR"), res
//                    .getString("C_ERROR_DOCBOOK_FILE_UNDEFINED"));
//
//            return false;
//        }

        String destPath = project.getDestinationPath();

        if (destPath == null || destPath.length() == 0) {

            ErrorBox.show(StaticContext.getDialogOwner(), ResourceServices.getString(res,"C_ERROR"), res
                    .getString("C_ERROR_PROJECT_DESTDIR_MUST_BE_SET"));

            return false;
        }

        if (checkData() == false) {
            return false;
        }

        return true;
    }
}
