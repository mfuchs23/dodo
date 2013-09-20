#!/bin/bash

function throw {
  echo "[Testfehler] $1"
  exit 255
}

function begin {
    echo $1
}

function end {
    echo 
    echo "---"
    echo
}

function validate {
  export SGML_CATALOG_FILES="../../../../ode/docbook/catalog.xml"
  xmllint --catalogs --postvalid --noout $1 || throw "xmllint fehlgeschlagen"
}
 
function test1 {

    begin "Test #1"
    dbdoclet ${options} -d test1 java/test/SimpleClass.java || throw "Test #1 fehlgeschlagen"
    validate test1/Reference.xml
    end
}

function test2 {

    begin "Test #2 (-overview)"
    dbdoclet ${options} -d test2 -overview java/overview1.html java/test/SimpleClass.java || throw "Test #2 fehlgeschlagen"
    validate test2/Reference.xml
    end
}

function test3 {

    begin "Test #3 (-overview)"
    dbdoclet ${options} -d test3 -overview java/overview1.html -doctitle "Test der Option -doctitle" java/test/SimpleClass.java || throw "Test #3 fehlgeschlagen"
    validate test3/Reference.xml
    end
}

function test4 {

    begin "Test #4 (-overview)"
    dbdoclet ${options} -d test4 -overview java/overview2.html java/test/SimpleClass.java || throw "Test #4 fehlgeschlagen"
    validate test4/Reference.xml
    end
}

function test5 {

    begin "Test #5 (-locale)"
    dbdoclet ${options} -d test5 -locale de java/test/SimpleClass.java || throw "Test #5 fehlgeschlagen"
    validate test5/Reference.xml
    grep -q "Gesamtsummen" test5/Reference.xml || throw "Test #5 fehlgeschlagen"
    end
}

function test6 {

    begin "Test #6 (-encoding)"
    dbdoclet ${options} -d test6 -encoding UTF-8 java/test/SimpleClass.java || throw "Test #6 fehlgeschlagen"
    validate test6/Reference.xml
    end
}

function test7 {

    begin "Test #7 (-version)"

    dbdoclet ${options} -d test7 -version java/test/SimpleClass.java || throw "Test #7 fehlgeschlagen"
    validate test7/Reference.xml
    grep -q "<term><emphasis>Version</emphasis></term>" test7/Reference.xml || throw "Test #7 fehlgeschlagen"

    dbdoclet ${options} -d test7 java/test/SimpleClass.java || throw "Test #7 fehlgeschlagen"
    validate test7/Reference.xml
    grep -q "<term><emphasis>Version</emphasis></term>" test7/Reference.xml 
    [ $? -eq 0 ] && throw "Test #7 fehlgeschlagen"
    end
}

function test8 {

    begin "Test #8 (-author)"

    dbdoclet ${options} -d test8 -author java/test/SimpleClass.java || throw "Test #8 fehlgeschlagen"
    validate test8/Reference.xml
    grep -q "<term><emphasis>Author</emphasis></term>" test8/Reference.xml || throw "Test #8 fehlgeschlagen"

    dbdoclet ${options} -d test8 java/test/SimpleClass.java || throw "Test #8 fehlgeschlagen"
    validate test8/Reference.xml
    grep -q "<term><emphasis>Author</emphasis></term>" test8/Reference.xml 
    [ $? -eq 0 ] && throw "Test #8 fehlgeschlagen"
    end
}

function test9 {

    begin "Test #9 (-windowtitle)"

    dbdoclet ${options} -d test9 -windowtitle "Buchtitel" java/test/SimpleClass.java || throw "Test #9 fehlgeschlagen"
    validate test9/Reference.xml
    grep -q "Buchtitel" test9/Reference.xml || throw "Test #9 fehlgeschlagen"
    end
}

function test10 {

    begin "Test #10 (-nodeprecated)"

    dbdoclet ${options} -d test10 java/test/SimpleClass.java || throw "Test #10 fehlgeschlagen"
    validate test10/Reference.xml
    grep -q "Testmarke @deprecated" test10/Reference.xml || throw "Test #10 Testmarke @deprecated fehlt."

    dbdoclet ${options} -d test10 -nodeprecated java/test/SimpleClass.java || throw "Test #10 fehlgeschlagen"
    validate test10/Reference.xml
    grep -q "Testmarke @deprecated" test10/Reference.xml
    [ $? -eq 1 ] || throw "Test #10 Ungültige Testmarke @deprecated."

    end
}

function test11 {

    begin "Test #11 (-noindex)"

    dbdoclet ${options} -d test11 java/test/SimpleClass.java || throw "Test #11 fehlgeschlagen"
    validate test11/Reference.xml
    grep -q "<index/>" test11/Reference.xml  || throw "Test #11 fehlgeschlagen"

    dbdoclet ${options} -d test11 -noindex java/test/SimpleClass.java || throw "Test #11 fehlgeschlagen"
    validate test11/Reference.xml
    grep -q "<index/>" test11/Reference.xml 
    [ $? -eq 1 ] || throw "Test #11 fehlgeschlagen"
    end
}

function test12 {

    begin "Test #12 (-nosince)"

    dbdoclet ${options} -d test12-1 java/test/SimpleClass.java || throw "Test #12 fehlgeschlagen"
    validate test12-1/Reference.xml
    grep -q "Testmarke @since" test12-1/Reference.xml || throw "Test #12 fehlgeschlagen"

    dbdoclet ${options} -d test12-2 -nosince java/test/SimpleClass.java || throw "Test #12 fehlgeschlagen"
    validate test12-2/Reference.xml
    grep -q "Testmarke @since" test12-2/Reference.xml 
    [ $? -eq 1 ] || throw "Test #12 fehlgeschlagen"
    end
}

function test13 {

    begin "Test #13 (-notree)"

    dbdoclet ${options} -d test13 -notree java/test/SimpleClass.java || throw "Test #13 fehlgeschlagen"
    validate test13/Reference.xml
    grep -q "ClassDiagram.svg" test13/Reference.xml 
    [ $? -eq 1 ] || throw "Test #13 fehlgeschlagen"
    end
}

function test14 {

    begin "Test #14 (-docencoding)"
    dbdoclet ${options} -d test14 -docencoding UTF-8 java/test/SimpleClass.java || throw "Test #14 fehlgeschlagen"
    validate test14/Reference.xml
    end
}

function test15 {

    begin "Test #15 (-abstract)"
    dbdoclet ${options} -d test15 -abstract "Kleine Zusammenfassung des Inhalts" java/test/SimpleClass.java || throw "Test #15 fehlgeschlagen"
    validate test15/Reference.xml
    grep -q "Kleine Zusammenfassung des Inhalts" test15/Reference.xml || throw "Test #15 fehlgeschlagen"
    end
}

function test16 {

    begin "Test #16 (-authorfirstname -authorsurname -authoremail)"
    dbdoclet ${options} -d test16 -authorfirstname Michael -authorsurname Fuchs -authoremail "michael.fuchs@unico-group.com" java/test/SimpleClass.java || throw "Test #16 fehlgeschlagen"
    validate test16/Reference.xml
    grep -q "michael.fuchs@unico-group.com" test16/Reference.xml || throw "Test #16 fehlgeschlagen"
    end
}

function test17 {

    begin "Test #17 (-codewidth)"
    dbdoclet ${options} -d test17 -codewidth 20 java/test/SimpleClass.java || throw "Test #17 fehlgeschlagen"
    validate test17/Reference.xml
    end
}

function test18 {

    begin "Test #18 (-copyrightholder)"
    dbdoclet ${options} -d test18 -copyrightholder "Michael Fuchs" java/test/SimpleClass.java || throw "Test #18 fehlgeschlagen"
    validate test18/Reference.xml
    grep -q "Michael Fuchs" test18/Reference.xml || throw "Test #18 fehlgeschlagen"
    end
}

function test19 {

    begin "Test #19 (-copyrightyear)"
    dbdoclet ${options} -d test19 -copyrightholder "Michael Fuchs" -copyrightyear "2001-2006" java/test/SimpleClass.java || throw "Test #19 fehlgeschlagen"
    validate test19/Reference.xml
    grep -q "2001-2006" test19/Reference.xml || throw "Test #19 fehlgeschlagen"
    end
}

function test20 {

    begin "Test #20 (-corporation)"
    dbdoclet ${options} -d test20 -corporation "UNICO GmbH" java/test/SimpleClass.java || throw "Test #20 fehlgeschlagen"
    validate test20/Reference.xml
    grep -q "UNICO GmbH" test20/Reference.xml || throw "Test #20 fehlgeschlagen"
    end
}

function test21 {

    begin "Test #21 (-dotfqn)"
    dbdoclet ${options} -d test21 -dotfqn java/test/SimpleClass.java || throw "Test #21 fehlgeschlagen"
    validate test21/Reference.xml
    end
}

function test22 {

    begin "Test #22 (-emphasisrole)"
    dbdoclet ${options} -d test22 -emphasisrole underline java/test/SimpleClass.java || throw "Test #22 fehlgeschlagen"
    validate test22/Reference.xml
    grep -q "role=\"underline\"" test22/Reference.xml || throw "Test #22 fehlgeschlagen"
    end
}

function test23 {

    begin "Test #23 (-f)"
    dbdoclet ${options} -d test23 -f Datei.xml java/test/SimpleClass.java || throw "Test #23 fehlgeschlagen"
    validate test23/Datei.xml
    end
}

function test24 {

    begin "Test #24 (-id)"
    dbdoclet ${options} -d test24 -id java java/test/SimpleClass.java || throw "Test #24 fehlgeschlagen"
    validate test24/Reference.xml
    grep -q "SIMPLECLASS.PUBLICMETHOD" test24/Reference.xml || throw "Test #24 fehlgeschlagen"
    end
}

function test25 {

    begin "Test #25 (-imgfmts)"
    dbdoclet ${options} -d test25 -imgfmts ps java/test/SimpleClass.java || throw "Test #25 fehlgeschlagen"
    validate test25/Reference.xml
    end
}

function test26 {

    begin "Test #26 (-imgpath)"
    dbdoclet ${options} -d test26 -imgpath images java/test/SimpleClass.java || throw "Test #26 fehlgeschlagen"
    validate test26/Reference.xml
    end
}

function test27 {

    begin "Test #27 (-include)"
    dbdoclet ${options} -d test27 -include ../java/overview1.xml,../java/overview2.xml java/test/SimpleClass.java || throw "Test #27 fehlgeschlagen"
    grep -q "<xi:include" test27/Reference.xml || throw "Test #27 fehlgeschlagen"
    end
}

function test28 {

    begin "Test #28 (-noexception)"

    dbdoclet ${options} -d test28-1 java/test/SimpleClass.java || throw "Test #28 fehlgeschlagen"
    validate test28-1/Reference.xml
    grep -q "Testmarke @exception" test28-1/Reference.xml || throw "Test #28 Testmarke @exception fehlt."

    dbdoclet ${options} -d test28-2 -noexception java/test/SimpleClass.java || throw "Test #28 fehlgeschlagen"
    validate test28-2/Reference.xml
    grep -q "Testmarke @exception" test28-2/Reference.xml 
    [ $? -eq 1 ] || throw "Test #28 Ungültige Testmarke @exception."
    end
}

function test29 {

    begin "Test #29 (-nometa)"

    dbdoclet ${options} -d test29-1 -author -version java/test/SimpleClass.java || throw "Test #29 fehlgeschlagen"
    validate test29-1/Reference.xml
    grep -q "Testmarke @author" test29-1/Reference.xml || throw "Test #29 Testmarke @author fehlt."
    grep -q "Testmarke @deprecated" test29-1/Reference.xml || throw "Test #29 Testmarke @deprecated fehlt."
    grep -q "Testmarke @since" test29-1/Reference.xml || throw "Test #29 Testmarke @since fehlt."
    grep -q "Testmarke @version" test29-1/Reference.xml || throw "Test #29 Testmarke @version fehlt."

    dbdoclet ${options} -d test29-2 -author -version -nometa java/test/SimpleClass.java || throw "Test #29 fehlgeschlagen"
    validate test29-2/Reference.xml
    grep -q "Testmarke @author" test29-2/Reference.xml 
    [ $? -eq 1 ] || throw "Test #29 Ungültige Testmarke @author."
    grep -q "Testmarke @deprecated" test29-2/Reference.xml 
    [ $? -eq 1 ] || throw "Test #29 Ungültige Testmarke @deprecated."
    grep -q "Testmarke @since" test29-2/Reference.xml 
    [ $? -eq 1 ] || throw "Test #29 Ungültige Testmarke @since."
    grep -q "Testmarke @version" test29-2/Reference.xml 
    [ $? -eq 1 ] || throw "Test #29 Ungültige Testmarke @version."
    end
}

function test30 {

    begin "Test #30 (-noparam)"

    dbdoclet ${options} -d test30-1 java/test/SimpleClass.java || throw "Test #30 fehlgeschlagen"
    validate test30-1/Reference.xml
    grep -q "Testmarke @param" test30-1/Reference.xml || throw "Test #30 Testmarke @param fehlt"

    dbdoclet ${options} -d test30-2 -noparam java/test/SimpleClass.java || throw "Test #30 fehlgeschlagen"
    validate test30-2/Reference.xml
    grep -q "Testmarke @param" test30-2/Reference.xml 
    [ $? -eq 1 ] || throw "Test #30 Ungültige Testmarke @param."
    end
}

function test31 {

    begin "Test #31 (-noprolog)"

    dbdoclet ${options} -d test31-1 java/test/SimpleClass.java || throw "Test #31 fehlgeschlagen"
    validate test31-1/Reference.xml
    grep -q "<?xml version=" test31-1/Reference.xml || throw "Test #31 XML-Prolog fehlt"

    dbdoclet ${options} -d test31-2 -noprolog java/test/SimpleClass.java || throw "Test #31 fehlgeschlagen"
    grep -q "<?xml version" test31-2/Reference.xml 
    [ $? -eq 1 ] || throw "Test #31 XML-Prolog gefunden."
    end
}

function test32 {

    begin "Test #32 (-nosee)"

    dbdoclet ${options} -d test32-1 java/test/SimpleClass.java || throw "Test #32 fehlgeschlagen"
    validate test32-1/Reference.xml
    grep -q "Testmarke @see" test32-1/Reference.xml || throw "Test #32 Testmarke @see fehlt"

    dbdoclet ${options} -d test32-2 -nosee java/test/SimpleClass.java || throw "Test #32 fehlgeschlagen"
    validate test32-2/Reference.xml
    grep -q "Testmarke @see" test32-2/Reference.xml 
    [ $? -eq 1 ] || throw "Test #32 Ungültige Testmarke @see."
    end
}

function test33 {

    begin "Test #33 (-noserialfield)"

    dbdoclet ${options} -d test33-1 java/test/SimpleClass.java || throw "Test #33 fehlgeschlagen"
    validate test33-1/Reference.xml
    grep -q "Testmarke @serialField" test33-1/Reference.xml || throw "Test #33 Testmarke @serialField fehlt"

    dbdoclet ${options} -d test33-2 -noserialfield java/test/SimpleClass.java || throw "Test #33 fehlgeschlagen"
    validate test33-2/Reference.xml
    grep -q "Testmarke @serialField" test33-2/Reference.xml 
    [ $? -eq 1 ] || throw "Test #33 Ungültige Testmarke @serialField."
    end
}

function test34 {

    begin "Test #34 (-nostatistics)"

    dbdoclet ${options} -d test34-1 java/test/SimpleClass.java || throw "Test #34 fehlgeschlagen"
    validate test34-1/Reference.xml
    grep -q "Statistics" test34-1/Reference.xml || throw "Test #34 Appendix Statistics fehlt"

    dbdoclet ${options} -d test34-2 -nostatistics java/test/SimpleClass.java || throw "Test #34 fehlgeschlagen"
    validate test34-2/Reference.xml
    grep -q "Statistics" test34-2/Reference.xml 
    [ $? -eq 1 ] || throw "Test #34 Ungültiger Appendix Statistics."
    end
}

function test35 {

    begin "Test #35 (-nosynopsis)"

    dbdoclet ${options} -d test35-1 java/test/SimpleClass.java || throw "Test #35 fehlgeschlagen"
    validate test35-1/Reference.xml
    grep -q "classsynopsis" test35-1/Reference.xml || throw "Test #35 Synopsis fehlt"

    dbdoclet ${options} -d test35-2 -nosynopsis java/test/SimpleClass.java || throw "Test #35 fehlgeschlagen"
    validate test35-2/Reference.xml
    grep -q "classsynopsis" test35-2/Reference.xml 
    [ $? -eq 1 ] || throw "Test #35 Ungültiger Abschnitt Synopsis."
    end
}

function test36 {

    begin "Test #36 (-notable)"

    dbdoclet ${options} -d test36-1 java/test/SimpleClass.java || throw "Test #36 fehlgeschlagen"
    validate test36-1/Reference.xml
    grep -q "informaltable" test36-1/Reference.xml || throw "Test #36 Tabelle fehlt"

    dbdoclet ${options} -d test36-2 -notable java/test/SimpleClass.java || throw "Test #36 fehlgeschlagen"
    validate test36-2/Reference.xml
    grep -q "informaltable" test36-2/Reference.xml 
    [ $? -eq 1 ] || throw "Test #36 Ungültige Tabelle."
    end
}

function test37 {

    begin "Test #37 (-noxreflabel)"

    dbdoclet ${options} -d test37-1 java/test/SimpleClass.java || throw "Test #37 fehlgeschlagen"
    validate test37-1/Reference.xml
    grep -q "xreflabel=" test37-1/Reference.xml || throw "Test #37 xref fehlt"

    dbdoclet ${options} -d test37-2 -noxreflabel  java/test/SimpleClass.java || throw "Test #37 fehlgeschlagen"
    validate test37-2/Reference.xml
    grep -q "xreflabel=" test37-2/Reference.xml 
    [ $? -eq 1 ] || throw "Test #37 Ungültiges Attribut xreflabel."
    end
}

function test38 {

    begin "Test #38 (-part)"

    dbdoclet ${options} -d test38-1 java/test/SimpleClass.java || throw "Test #38 fehlgeschlagen"
    validate test38-1/Reference.xml
    grep -q "<book" test38-1/Reference.xml || throw "Test #38 Element book fehlt"

    dbdoclet ${options} -d test38-2 -part  java/test/SimpleClass.java || throw "Test #38 fehlgeschlagen"
    validate test38-2/Reference.xml
    grep -q "<part" test38-2/Reference.xml || throw "Test #38 Element part fehlt"
    end

    dbdoclet ${options} -d test38-1 java/test/SimpleClass.java || throw "Test #38 fehlgeschlagen"
    validate test38-1/Reference.xml
    grep -q "<book" test38-1/Reference.xml || throw "Test #38 Element book fehlt"

    dbdoclet ${options} -d test38-2 -part  java/test/SimpleClass.java || throw "Test #38 fehlgeschlagen"
    validate test38-2/Reference.xml
    grep -q "<part" test38-2/Reference.xml || throw "Test #38 Element part fehlt"
    end
}

function test39 {

    begin "Test #39 (-tableframe)"

    dbdoclet ${options} -d test39 -tableframe sides java/test/SimpleClass.java || throw "Test #39 fehlgeschlagen"
    validate test39/Reference.xml
}

function test40 {

    begin "Test #40 (-nofields)"

    dbdoclet ${options} -d test40-1 java/test/SimpleClass.java || throw "Test #40 fehlgeschlagen"
    validate test40-1/Reference.xml
    grep -q "FIELD-1-1" test40-1/Reference.xml || throw "Test #40 Abschnitt fields fehlt"

    dbdoclet ${options} -d test40-2 -nofields java/test/SimpleClass.java || throw "Test #40 fehlgeschlagen"
    validate test40-2/Reference.xml
    grep -q "FIELD-1-1" test40-2/Reference.xml 
    [ $? -eq 1 ] || throw "Test #40 Ungültiger Abschnitt fields."
    end
}

function test41 {

    begin "Test #41 (-nomethods)"

    dbdoclet ${options} -d test41-1 java/test/SimpleClass.java || throw "Test #41 fehlgeschlagen"
    validate test41-1/Reference.xml
    grep -q "METHOD-1-1" test41-1/Reference.xml || throw "Test #41 Abschnitt methods fehlt"

    dbdoclet ${options} -d test41-2 -nomethods java/test/SimpleClass.java || throw "Test #41 fehlgeschlagen"
    validate test41-2/Reference.xml
    grep -q "METHOD-1-1" test41-2/Reference.xml 
    [ $? -eq 1 ] || throw "Test #41 Ungültiger Abschnitt methods."
    end
}

function test42 {

    begin "Test #42 (-noinheritedfrom)"

    dbdoclet ${options} -d test42-1 java/test/SimpleClass.java || throw "Test #42 fehlgeschlagen"
    validate test42-1/Reference.xml
    grep -q "Methods inherited from" test42-1/Reference.xml || throw "Test #42 Abschnitt inheritedfrom fehlt"

    dbdoclet ${options} -d test42-2 -noinheritedfrom java/test/SimpleClass.java || throw "Test #42 fehlgeschlagen"
    validate test42-2/Reference.xml
    grep -q "Methods inherited from" test42-2/Reference.xml 
    [ $? -eq 1 ] || throw "Test #42 Ungültiger Abschnitt inheritedfrom."
    end
}

function test43 {

    begin "Test #43 (-useappendix)"

    dbdoclet ${options} -d test43-1 java/test/SimpleClass.java || throw "Test #43 fehlgeschlagen"
    validate test43-1/Reference.xml
    grep -q "<appendix>" test43-1/Reference.xml 
    [ $? -eq 1 ] || throw "Test #43 Ungültiger Abschnitt appendix."

    dbdoclet ${options} -d test43-2 -useappendix java/test/SimpleClass.java || throw "Test #43 fehlgeschlagen"
    validate test43-2/Reference.xml
    grep -q "<appendix>" test43-2/Reference.xml || throw "Test #43 Appendix fehlt"
    end
}

function test44 {

    begin "Test #44 (-tag)"

    dbdoclet ${options} -d test44 -tag todo:a:"Noch zu tun:" java/test/SimpleClass.java || throw "Test #44 fehlgeschlagen"
    validate test44/Reference.xml
    grep -q "Testmarke @todo" test44/Reference.xml || throw "Test #44 Testmarke @todo fehlt"
}

options=""
jdk=1.5
number=0
start=1

while getopts "j:n:o:s:" arg ; do
    case $arg in
        j) jdk=$OPTARG;;
        n) number=$OPTARG;;
        o) options=$OPTARG;;
        s) start=$OPTARG;;
    esac
done

rm -rf test*

. swjdk -j ${jdk}

if [ ${number} -gt 0 ] ; then
  eval "test${number}"
  exit 0
fi

number=${start}
while [ $number -le 43 ] ; do
  eval "test${number}"
  number=$(( ${number} + 1 ))
done

