

;Include Modern UI

!include "MUI.nsh"

; The name of the installer
Name "tidbit"

; The file to write
OutFile "tidbit.exe"

; The default installation directory
InstallDir "$PROGRAMFILES\tidbit"

;--------------------------------
; Interface Settings

!define MUI_ABORTWARNING
!define MUI_ICON "root\Programme\tidbit\icons\tidbit.ico"
!define MUI_UNICON "root\Programme\tidbit\icons\tidbit.ico"

!insertmacro MUI_PAGE_LICENSE "root\Programme\tidbit\License.txt"
; !insertmacro MUI_PAGE_COMPONENTS
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES
  
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES

;--------------------------------
;Languages

!insertmacro MUI_LANGUAGE "english"
!insertmacro MUI_LANGUAGE "french"
!insertmacro MUI_LANGUAGE "german"
!insertmacro MUI_LANGUAGE "italian"
!insertmacro MUI_LANGUAGE "swedish"

Section install

  SetOutPath $INSTDIR
  File /r "root\Programme\tidbit\*.*" 

  WriteUninstaller uninstall.exe

SectionEnd

Section Uninstall
  RMDir /r "$INSTDIR"
SectionEnd

;;
;; Callback functions
;;

Function .onInit
  !insertmacro MUI_LANGDLL_DISPLAY
FunctionEnd
  