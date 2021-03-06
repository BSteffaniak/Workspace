; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{066BABED-F417-49FE-912C-AF7406644B2C}
AppName=ArrowIDE
AppVersion=0.7.6
;AppVerName=ArrowIDE 0.7.6
AppPublisher=FoxyCorndog
DefaultDirName={pf}\ArrowIDE
DefaultGroupName=ArrowIDE
AllowNoIcons=yes
OutputDir=C:\Users\Braden Steffaniak\Documents\GitHub\Workspace\ArrowIDE\installer
OutputBaseFilename=setupArrowIDE
Compression=lzma
SolidCompression=yes
ChangesAssociations=yes
SetupIconFile=C:\Users\Braden Steffaniak\Documents\GitHub\Workspace\ArrowIDE\iconlarge.ico

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked
Name: "quicklaunchicon"; Description: "{cm:CreateQuickLaunchIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked; OnlyBelowVersion: 0,6.1
Name: txtAssociation; Description: "Associate ""txt"" extension"; GroupDescription: File extensions:
Name: javaAssociation; Description: "Associate ""java"" extension"; GroupDescription: File extensions:
Name: cAssociation; Description: "Associate ""c"" extension"; GroupDescription: File extensions:
Name: cppAssociation; Description: "Associate ""cpp"" extension"; GroupDescription: File extensions:
Name: hAssociation; Description: "Associate ""h"" extension"; GroupDescription: File extensions:
Name: pyAssociation; Description: "Associate ""py"" extension"; GroupDescription: File extensions:
Name: vsAssociation; Description: "Associate ""vs"" extension"; GroupDescription: File extensions:
Name: vertAssociation; Description: "Associate ""vert"" extension"; GroupDescription: File extensions:
Name: fsAssociation; Description: "Associate ""fs"" extension"; GroupDescription: File extensions:
Name: fragAssociation; Description: "Associate ""frag"" extension"; GroupDescription: File extensions:
Name: shadeAssociation; Description: "Associate ""shade"" extension"; GroupDescription: File extensions:
Name: shadAssociation; Description: "Associate ""shad"" extension"; GroupDescription: File extensions:
Name: shaAssociation; Description: "Associate ""sha"" extension"; GroupDescription: File extensions:
Name: asmAssociation; Description: "Associate ""asm"" extension"; GroupDescription: File extensions:
Name: foxyAssociation; Description: "Associate ""foxy"" extension"; GroupDescription: File extensions:
Name: rtfAssociation; Description: "Associate ""rtf"" extension"; GroupDescription: File extensions:
Name: phpAssociation; Description: "Associate ""php"" extension"; GroupDescription: File extensions:
Name: php5Association; Description: "Associate ""php5"" extension"; GroupDescription: File extensions:

[Files]
Source: "C:\Users\Braden Steffaniak\Documents\GitHub\Workspace\ArrowIDE\ArrowIDE.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\Braden Steffaniak\Desktop\crap\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
;Source: "C:\Users\Braden Steffaniak\Documents\GitHub\Workspace\ArrowIDE\arrow.config"; DestDir: "{app}"; Flags: ignoreversion
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{group}\ArrowIDE"; Filename: "{app}\ArrowIDE.exe"
Name: "{group}\{cm:UninstallProgram,ArrowIDE}"; Filename: "{uninstallexe}"
Name: "{commondesktop}\ArrowIDE"; Filename: "{app}\ArrowIDE.exe"; Tasks: desktopicon
Name: "{userappdata}\Microsoft\Internet Explorer\Quick Launch\ArrowIDE"; Filename: "{app}\ArrowIDE.exe"; Tasks: quicklaunchicon

[Run]
Filename: "{app}\ArrowIDE.exe"; Description: "{cm:LaunchProgram,ArrowIDE}"; Flags: nowait postinstall skipifsilent

[Registry]
Root: HKCR; Subkey: ".txt"; ValueType: string; ValueName: ""; ValueData: "TextFile"; Flags: uninsdeletevalue; Tasks: txtAssociation
Root: HKCR; Subkey: ".java"; ValueType: string; ValueName: ""; ValueData: "JavaFile"; Flags: uninsdeletevalue; Tasks: javaAssociation
Root: HKCR; Subkey: ".c"; ValueType: string; ValueName: ""; ValueData: "CFile"; Flags: uninsdeletevalue; Tasks: cAssociation
Root: HKCR; Subkey: ".cpp"; ValueType: string; ValueName: ""; ValueData: "CppFile"; Flags: uninsdeletevalue; Tasks: cppAssociation
Root: HKCR; Subkey: ".h"; ValueType: string; ValueName: ""; ValueData: "HeaderFile"; Flags: uninsdeletevalue; Tasks: hAssociation
Root: HKCR; Subkey: ".py"; ValueType: string; ValueName: ""; ValueData: "PythonFile"; Flags: uninsdeletevalue; Tasks: pyAssociation
Root: HKCR; Subkey: ".vs"; ValueType: string; ValueName: ""; ValueData: "VertexFile"; Flags: uninsdeletevalue; Tasks: vsAssociation
Root: HKCR; Subkey: ".vert"; ValueType: string; ValueName: ""; ValueData: "VertexFile"; Flags: uninsdeletevalue; Tasks: vertAssociation
Root: HKCR; Subkey: ".fs"; ValueType: string; ValueName: ""; ValueData: "FragmentFile"; Flags: uninsdeletevalue; Tasks: fsAssociation
Root: HKCR; Subkey: ".frag"; ValueType: string; ValueName: ""; ValueData: "FragmentFile"; Flags: uninsdeletevalue; Tasks: fragAssociation
Root: HKCR; Subkey: ".shade"; ValueType: string; ValueName: ""; ValueData: "ShaderFile"; Flags: uninsdeletevalue; Tasks: shadeAssociation
Root: HKCR; Subkey: ".shad"; ValueType: string; ValueName: ""; ValueData: "ShaderFile"; Flags: uninsdeletevalue; Tasks: shadAssociation
Root: HKCR; Subkey: ".sha"; ValueType: string; ValueName: ""; ValueData: "ShaderFile"; Flags: uninsdeletevalue; Tasks: shaAssociation
Root: HKCR; Subkey: ".asm"; ValueType: string; ValueName: ""; ValueData: "AssemblyFile"; Flags: uninsdeletevalue; Tasks: asmAssociation
Root: HKCR; Subkey: ".foxy"; ValueType: string; ValueName: ""; ValueData: "FoxyFile"; Flags: uninsdeletevalue; Tasks: foxyAssociation
Root: HKCR; Subkey: ".rtf"; ValueType: string; ValueName: ""; ValueData: "RichTextFile"; Flags: uninsdeletevalue; Tasks: rtfAssociation
Root: HKCR; Subkey: ".php"; ValueType: string; ValueName: ""; ValueData: "PHPFile"; Flags: uninsdeletevalue; Tasks: phpAssociation
Root: HKCR; Subkey: ".php5"; ValueType: string; ValueName: ""; ValueData: "PHP5File"; Flags: uninsdeletevalue; Tasks: php5Association

