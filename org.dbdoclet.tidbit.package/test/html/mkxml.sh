#!/bin/bash

# /*
# 
# %%% Copyright (C) 2001-2003 Michael Fuchs %%%
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2, or (at your option)
# any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the
# Free Software Foundation, Inc., 59 Temple Place - Suite 330,
# Boston, MA 02111-1307, USA.
#	
# Author: Michael Fuchs
# E-Mail: mfuchs@unico-consulting.com
#
# RCS Information:
# ---------------
# Id.........: $Id: mkxml.sh,v 1.1.1.1 2004/12/21 13:59:28 mfuchs Exp $
# Author.....: $Author: mfuchs $
# Date.......: $Date: 2004/12/21 13:59:28 $
# Revision...: $Revision: 1.1.1.1 $
# State......: $State: Exp $
#
# */


XML=../xml/Files.xml

(
echo "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>"
echo "<test-files>" 
for FILE in `ls -1 *.html` 
do
  echo "  <file>$FILE</file>"
done
echo "</test-files>"
) > $XML  

# /*
# $Log: mkxml.sh,v $
# Revision 1.1.1.1  2004/12/21 13:59:28  mfuchs
# Reimport
#
# Revision 1.1.1.1  2004/02/17 22:48:40  mfuchs
# dbdoclet
#
# Revision 1.1.1.1  2004/01/05 14:56:14  cvs
# dbdoclet
#
# Revision 1.1.1.1  2003/08/01 13:17:58  cvs
# DocBook Doclet
#
# Revision 1.1.1.1  2003/07/31 17:05:39  mfuchs
# DocBook Doclet since 0.46
#
# Revision 1.1.1.1  2003/05/30 11:09:40  mfuchs
# dbdoclet
#
# Revision 1.1.1.1  2003/03/18 07:41:37  mfuchs
# DocBook Doclet 0.40
#
# Revision 1.1.1.1  2003/03/17 20:50:19  cvs
# dbdoclet
#
# */