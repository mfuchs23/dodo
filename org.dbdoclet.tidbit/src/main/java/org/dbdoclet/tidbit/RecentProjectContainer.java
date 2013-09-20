/*
 * ### Copyright (C) 2001-2003 Michael Fuchs ###
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * Author: Michael Fuchs
 * E-Mail: mfuchs@unico-consulting.com
 *
 * RCS Information:
 * ---------------
 * Id.........: $Id: RecentProjectContainer.java,v 1.1.1.1 2004/12/21 14:01:46 mfuchs Exp $
 * Author.....: $Author: mfuchs $
 * Date.......: $Date: 2004/12/21 14:01:46 $
 * Revision...: $Revision: 1.1.1.1 $
 * State......: $State: Exp $
 */
package org.dbdoclet.tidbit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class RecentProjectContainer implements Serializable {

    private static final long serialVersionUID = 1L;

    private final static String m_home = System.getProperty("user.home");
    private final static String m_fsep = System.getProperty("file.separator");

    private static String getRecentFilePath() {

        File path;
        String home = "";

        if ((m_home != null) && (m_home.length() > 0)) {

            home = m_home + m_fsep + ".dbdoclet" + m_fsep;

            path = new File(home);
            path.mkdirs();
        }

        return home + "recent.bin";
    }

    public static RecentProjectContainer load() {

        RecentProjectContainer recent = null;

        try {

            FileInputStream istream = new FileInputStream(getRecentFilePath());
            ObjectInputStream p = new ObjectInputStream(istream);

            recent = (RecentProjectContainer) p.readObject();
            istream.close();
        
        } catch (Exception oops) {

            return new RecentProjectContainer();
        } // end of catch

        return recent;
    }

    private ArrayList<String> m_recent = new ArrayList<String>();

    public void add(String fname) {

        if (m_recent.contains(fname)) {
            m_recent.remove(fname);
        }

        while (m_recent.size() > 4) {
            m_recent.remove(m_recent.size() - 1);
        }
        
        m_recent.add(0, fname);
    }

    public ArrayList<String> getList() {

        return m_recent;
    }

    public void save() {

        try {

            FileOutputStream ostream = new FileOutputStream(getRecentFilePath());
            ObjectOutputStream p = new ObjectOutputStream(ostream);

            p.writeObject(this);
            p.flush();

            ostream.close();
            
        } catch (Exception oops) {

            oops.printStackTrace();
        }
    }
}

/*
 * $Log: RecentProjectContainer.java,v $ Revision 1.1.1.1 2004/12/21 14:01:46
 * mfuchs Reimport
 * 
 * Revision 1.3 2004/10/20 19:33:54 mfuchs Korrekturen
 * 
 * Revision 1.2 2004/10/05 13:13:19 mfuchs Sicherung
 * 
 * Revision 1.1.1.1 2004/02/17 22:51:21 mfuchs dbdoclet
 * 
 * Revision 1.1.1.1 2004/01/05 14:58:59 cvs dbdoclet
 * 
 * Revision 1.2 2003/08/18 12:39:52 mfuchs Code cleanup amd minor improvements.
 * 
 * Revision 1.1.1.1 2003/08/01 13:19:42 cvs DocBook Doclet
 * 
 * Revision 1.1.1.1 2003/07/31 17:05:40 mfuchs DocBook Doclet since 0.46
 * 
 * Revision 1.1.1.1 2003/05/30 11:09:40 mfuchs dbdoclet
 * 
 * Revision 1.1.1.1 2003/03/18 07:41:37 mfuchs DocBook Doclet 0.40
 * 
 * Revision 1.1.1.1 2003/03/17 20:51:48 cvs dbdoclet
 */
