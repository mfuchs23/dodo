println("Website")

String top = new File("website.header").text;

def ant = new AntBuilder();

ant.sequential() {
    delete(dir: "/home/michael/Internetauftritt/projects/dbdoclet")
    copy(todir: "/home/michael/Internetauftritt/projects/dbdoclet") {
        fileset(dir: "tutorial/html")
    }
}

def closure = { file ->
    
    if (file.name.endsWith(".html")) {
        fileText = file.text;
        fileText = fileText.replaceAll("<div class=\"navheader\">", top + "<div class=\"navheader\">");
        fileText = fileText.replaceAll("Tutorial.css","../../dbdoclet.css");
        fileText = fileText.replaceAll("</body>", "</div></div><div id='extra'></div><div id='footer'><p>Copyright &copy; 2011, Michael Fuchs</p></div></body>");
        file.write(fileText);
    }
}

File dir = new File("/home/michael/Internetauftritt/projects/dbdoclet");
dir.eachFileRecurse closure
