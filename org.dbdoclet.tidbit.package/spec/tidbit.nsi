; tidbit.nsi
;--------------------------------

;Include Modern UI

!include "MUI.nsh"
!include "FileFunc.nsh" ;required for target dir validation
!insertmacro "DirState"

; The name of the installer
Name "tidbit"

; The file to write
OutFile @OutFile@

; The default installation directory
InstallDir "$PROGRAMFILES\DocBook Doclet\tidbit"

;--------------------------------
;Interface Settings

!define MUI_ABORTWARNING
!define MUI_ICON "root\Programme\tidbit\icons\48x48\dodo.ico"
!define MUI_UNICON "root\Programme\tidbit\icons\48x48\dodo.ico"

;--------------------------------
;Pages

!define MUI_PAGE_CUSTOMFUNCTION_LEAVE "CheckPreconditions"
; !insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_LICENSE "root\Programme\tidbit\LICENSE"
; !insertmacro MUI_PAGE_COMPONENTS
!define MUI_PAGE_CUSTOMFUNCTION_LEAVE "ValidateInstallationDir"
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES
  
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES

;--------------------------------
;Languages

!insertmacro MUI_LANGUAGE "English"
!insertmacro MUI_LANGUAGE "German"
!insertmacro MUI_LANGUAGE "French"
!insertmacro MUI_LANGUAGE "Spanish"
;!insertmacro MUI_LANGUAGE "Arabic"
;!insertmacro MUI_LANGUAGE "Bulgarian"
;!insertmacro MUI_LANGUAGE "Croatian"
;!insertmacro MUI_LANGUAGE "Czech"
;!insertmacro MUI_LANGUAGE "Danish"
;!insertmacro MUI_LANGUAGE "Dutch"
;!insertmacro MUI_LANGUAGE "Estonian"
;!insertmacro MUI_LANGUAGE "Farsi"
;!insertmacro MUI_LANGUAGE "Finnish"
;!insertmacro MUI_LANGUAGE "Greek"
;!insertmacro MUI_LANGUAGE "Hebrew"
;!insertmacro MUI_LANGUAGE "Hungarian"
;!insertmacro MUI_LANGUAGE "Indonesian"
;!insertmacro MUI_LANGUAGE "Italian"
;!insertmacro MUI_LANGUAGE "Japanese"
;!insertmacro MUI_LANGUAGE "Korean"
;!insertmacro MUI_LANGUAGE "Latvian"
;!insertmacro MUI_LANGUAGE "Lithuanian"
;!insertmacro MUI_LANGUAGE "Macedonian"
;!insertmacro MUI_LANGUAGE "Norwegian"
;!insertmacro MUI_LANGUAGE "Polish"
;!insertmacro MUI_LANGUAGE "Portuguese"
;!insertmacro MUI_LANGUAGE "PortugueseBR"
;!insertmacro MUI_LANGUAGE "Romanian"
;!insertmacro MUI_LANGUAGE "Russian"
;!insertmacro MUI_LANGUAGE "Serbian"
;!insertmacro MUI_LANGUAGE "SimpChinese"
;!insertmacro MUI_LANGUAGE "Slovak"
;!insertmacro MUI_LANGUAGE "Slovenian"
;!insertmacro MUI_LANGUAGE "Swedish"
;!insertmacro MUI_LANGUAGE "Thai"
;!insertmacro MUI_LANGUAGE "TradChinese"
;!insertmacro MUI_LANGUAGE "Turkish"
;!insertmacro MUI_LANGUAGE "Ukrainian"

LangString MsgInstallDirNotEmpty ${LANG_ENGLISH} "The installation directory must be empty!"
LangString MsgAlreadyInstalled   ${LANG_ENGLISH} "DocBook Doclet is already installed. It is recommended to uninstall the previous version. Press OK to start the uninstallation process, press Cancel proceed."

LangString MsgInstallDirNotEmpty ${LANG_GERMAN} "Das Installationsverzeichnis muß leer sein! Bitte deinstallieren Sie ältere Versionen von DocBook Doclet."
LangString MsgAlreadyInstalled   ${LANG_GERMAN} "DocBook Doclet ist bereits installiert. Bitte löschen Sie bestehende Installation, bevor Sie fortfahren. Um die Deinstallation jetzt zu starten drücken Sie bitte auf OK, um die Installation fortzusetzen klicken Sie auf Abbrechen."

LangString MsgInstallDirNotEmpty ${LANG_FRENCH} "Le répertoire d'installation doit être vide!"
LangString MsgAlreadyInstalled   ${LANG_FRENCH} "DocBook Doclet est déjà installé. Il est recommandé de désinstaller la version précédente. Appuyez sur OK pour démarrer le processus de désinstallation, cliquez sur Annuler procéder."

LangString MsgInstallDirNotEmpty ${LANG_SPANISH} "El directorio de instalación debe estar vacía!"
LangString MsgAlreadyInstalled   ${LANG_SPANISH} "DocBook Doclet ya está instalado. Se recomienda desinstalar la versión anterior. Pulse Aceptar para iniciar el proceso de desinstalación, pulse Cancelar proceder."

;--------------------------------

; The stuff to install
Section tidbit

  ; Set output path to the installation directory.
  SetOutPath $INSTDIR

  SetShellVarContext "all"

  CreateDirectory "$SMPROGRAMS\DocBook Doclet"

  CreateShortCut '$SMPROGRAMS\DocBook Doclet\Dodo.lnk' 'javaw'  '-Dorg.dbdoclet.doclet.home="$INSTDIR" -jar "$INSTDIR\jars\dodo.jar"' '$INSTDIR\icons\48x48\dodo.ico'
  CreateShortCut "$SMPROGRAMS\DocBook Doclet\Documentation (HTML).lnk" "$INSTDIR\doc\tutorial\html\index.html"
  CreateShortCut "$SMPROGRAMS\DocBook Doclet\Documentation (PDF).lnk" "$INSTDIR\doc\tutorial\Tutorial.pdf"
  CreateShortCut "$SMPROGRAMS\DocBook Doclet\Uninstall.lnk" "$INSTDIR\uninstall.exe" "" "$INSTDIR\uninstall.exe" 0

  CreateShortCut '$DESKTOP\Dodo.lnk' 'javaw'  '-Dorg.dbdoclet.doclet.home="$INSTDIR" -jar "$INSTDIR\jars\dodo.jar"' '$INSTDIR\icons\48x48\dodo.ico'

  ; evh::AppendToEnvVarValue "PATH" ";" "$INSTDIR\bin"
  ; evh::SetEnvVarValue "DBDOCLET_HOME" "$INSTDIR"

  ; Put file there
  File /r "root\Programme\tidbit\*.*"
  
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\DocBook Doclet" "DisplayName" "DocBook Doclet"
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\DocBook Doclet" "UninstallString" "$INSTDIR\Uninstall.exe"
  WriteUninstaller "Uninstall.exe"

SectionEnd 

; Uninstall section

Section Uninstall
    
    SetShellVarContext "all"

    Delete '$DESKTOP\tidbit.lnk'
    RMDir /r "$SMPROGRAMS\DocBook Doclet"
    RMDir /r "$INSTDIR"

SectionEnd

Function .onInit

  !insertmacro MUI_LANGDLL_DISPLAY

FunctionEnd

Function CheckPreconditions

  ReadRegStr $R0 HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\DocBook Doclet" "UninstallString"

  StrCmp $R0 "" done cleanup

  cleanup:
  MessageBox MB_OKCANCEL|MB_ICONQUESTION $(MsgAlreadyInstalled) IDCANCEL done
  ExecWait $R0
  
  done:

FunctionEnd

Function ValidateInstallationDir
    ;make sure $INSTDIR path is either empty or does not exist.
    Push $0
    ${DirState} "$INSTDIR" $0
    ${If} $0 == 1   ;folder is full.  (other values: 0: empty, -1: not found)
        ;refuse selection
        MessageBox MB_OK|MB_ICONSTOP $(MsgInstallDirNotEmpty)
        Abort
    ${EndIf}
    Pop $0
FunctionEnd