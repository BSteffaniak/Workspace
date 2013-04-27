package net.foxycorndog.arrowide.language.glsl;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.language.Keyword;

import org.eclipse.swt.graphics.Color;

import static net.foxycorndog.arrowide.language.Language.GLSL;
import static net.foxycorndog.arrowide.language.glsl.GLSLLanguage.KEYWORD_COLOR;
import static net.foxycorndog.arrowide.language.glsl.GLSLLanguage.VERSION_COLOR;

public class GLSLKeyword
{
	public static Keyword
			INT, FLOAT, DOUBLE, VEC2, VEC3, VEC4, MAT2, MAT3, MAT4, VOID,
			VERSION, VARYING, ATTRIBUTE, UNIFORM, SAMPLER2D,
			GL_FRAGCOLOR, GL_VERTEX, GL_NORMAL, GL_NORMALMATRIX,
			GL_MODELVIEWMATRIX, GL_PROJECTIONMATRIX, GL_MODELVIEWPROJECTIONMATRIX,
			GL_POSITION, GL_TEXCOORD, GL_MULTITEXCOORD0, GL_MULTITEXCOORD1,
			GL_MULTITEXCOORD2, GL_MULTITEXCOORD3, GL_MULTITEXCOORD4,
			GL_MULTITEXCOORD5, GL_MULTITEXCOORD6, GL_MULTITEXCOORD7,
			IF, ELSE, FOR, WHILE, GL_LIGHTMODEL;
	
	public static void init()
	{
		INT                          = new Keyword(GLSL, "int", KEYWORD_COLOR);
		FLOAT                        = new Keyword(GLSL, "float", KEYWORD_COLOR);
		DOUBLE                       = new Keyword(GLSL, "double", KEYWORD_COLOR);
		VEC2                         = new Keyword(GLSL, "vec2", KEYWORD_COLOR);
		VEC3                         = new Keyword(GLSL, "vec3", KEYWORD_COLOR);
		VEC4                         = new Keyword(GLSL, "vec4", KEYWORD_COLOR);
		MAT2                         = new Keyword(GLSL, "mat2", KEYWORD_COLOR);
		MAT3                         = new Keyword(GLSL, "mat3", KEYWORD_COLOR);
		MAT4                         = new Keyword(GLSL, "mat4", KEYWORD_COLOR);
		VOID                         = new Keyword(GLSL, "void", KEYWORD_COLOR);
		VERSION                      = new Keyword(GLSL, "#version", VERSION_COLOR);
		VARYING                      = new Keyword(GLSL, "varying", KEYWORD_COLOR);
		ATTRIBUTE                    = new Keyword(GLSL, "attribute", KEYWORD_COLOR);
		UNIFORM                      = new Keyword(GLSL, "uniform", KEYWORD_COLOR);
		SAMPLER2D                    = new Keyword(GLSL, "sampler2D", KEYWORD_COLOR);
		GL_FRAGCOLOR                 = new Keyword(GLSL, "gl_FragColor", KEYWORD_COLOR);
		GL_VERTEX                    = new Keyword(GLSL, "gl_Vertex", KEYWORD_COLOR);
		GL_NORMAL                    = new Keyword(GLSL, "gl_Normal", KEYWORD_COLOR);
		GL_NORMALMATRIX              = new Keyword(GLSL, "gl_NormalMatrix", KEYWORD_COLOR);
		GL_MODELVIEWMATRIX           = new Keyword(GLSL, "gl_ModelViewMatrix", KEYWORD_COLOR);
		GL_PROJECTIONMATRIX          = new Keyword(GLSL, "gl_ProjectionMatrix", KEYWORD_COLOR);
		GL_MODELVIEWPROJECTIONMATRIX = new Keyword(GLSL, "gl_ModelViewProjectionMatrix", KEYWORD_COLOR);
		GL_POSITION                  = new Keyword(GLSL, "gl_Position", KEYWORD_COLOR);
		GL_TEXCOORD                  = new Keyword(GLSL, "gl_TexCoord", KEYWORD_COLOR);
		GL_MULTITEXCOORD0            = new Keyword(GLSL, "gl_MultiTexCoord0", KEYWORD_COLOR);
		GL_MULTITEXCOORD1            = new Keyword(GLSL, "gl_MultiTexCoord1", KEYWORD_COLOR);
		GL_MULTITEXCOORD2            = new Keyword(GLSL, "gl_MultiTexCoord2", KEYWORD_COLOR);
		GL_MULTITEXCOORD3            = new Keyword(GLSL, "gl_MultiTexCoord3", KEYWORD_COLOR);
		GL_MULTITEXCOORD4            = new Keyword(GLSL, "gl_MultiTexCoord4", KEYWORD_COLOR);
		GL_MULTITEXCOORD5            = new Keyword(GLSL, "gl_MultiTexCoord5", KEYWORD_COLOR);
		GL_MULTITEXCOORD6            = new Keyword(GLSL, "gl_MultiTexCoord6", KEYWORD_COLOR);
		GL_MULTITEXCOORD7            = new Keyword(GLSL, "gl_MultiTexCoord7", KEYWORD_COLOR);
		IF                           = new Keyword(GLSL, "if", KEYWORD_COLOR);
		ELSE                         = new Keyword(GLSL, "else", KEYWORD_COLOR);
		FOR                          = new Keyword(GLSL, "for", KEYWORD_COLOR);
		WHILE                        = new Keyword(GLSL, "while", KEYWORD_COLOR);
		GL_LIGHTMODEL                = new Keyword(GLSL, "gl_LightModel", KEYWORD_COLOR);
	}
}