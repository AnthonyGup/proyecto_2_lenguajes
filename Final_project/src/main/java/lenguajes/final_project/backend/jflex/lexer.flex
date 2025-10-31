/*CABECERA*/
package lenguajes.final_project.backend.jflex;
import lenguajes.final_project.backend.token.Token;
import lenguajes.final_project.backend.token.TokenType;
import lenguajes.final_project.backend.position.Position;
import java.util.ArrayList;

%%

/*CONFIGURACIONES ========================================================================================================== */
%class Lexer
%public
%unicode
%line
%column
%char
%type Token

/*CODIGO JAVA ============================================================================================================== */
%{
    ArrayList<Token> tokens = new ArrayList<>();

    public void guardarToken(Token t){
        if(t.getTipo2() == null) {
            t.setTipo2(t.getTipo());
        }
        tokens.add(t);
    }

    public ArrayList<Token> getTokens(){
        return tokens;
    }

%}

/*EXPRESIONES REGULARES  IDENTIFICADORES, NUMEROS, DECIMALES, PALABRAS RESERVADAS */

Identificador = [:jletter:] [:jletterdigit:]*
DIGITO = [0-9]
Numero = (0|([1-9][0-9]*))
Decimal = ({Numero}\.{DIGITO}+)
LineTerminator = \r\n|\r|\n
WhiteSpace     = [ \t\f]+

/* Palabras reservadas: las cubrimos en mayúsculas y minúsculas explícitamente para evitar ambigüedad */
SI = ( "si" | "SI" )
ENTONCES = ( "entonces" | "ENTONCES" )
ENTERO = ( "entero" | "ENTERO" )
NUMERO = ( "numero" | "NUMERO" )
CADENA_WORD = ( "cadena" | "CADENA" )
ESCRIBIR = ( "escribir" | "ESCRIBIR" )
DEFINIR = ( "definir" | "DEFINIR" )
COMO = ( "como" | "COMO" )

/* Otros */
Puntuacion = ( "." | "," | ";" | ":" )
Agrupacion = ( "(" | ")" | "[" | "]" | "{" | "}" | "<" | ">")

COMILLA = \"
LineCommentStart = "//"
BlockCommentStart = "/\\*"
BlockCommentEnd = "\\*/"

%%

/*ACCIONES*/

/* ================================= -- Comentario de bloque correcto -- ================================= */
"/*"([^*]|\*+[^*/])*"\*/" {
    guardarToken(new Token(TokenType.COMENTARIO_BLOQUE, yytext(), (int)yychar, new Position(yyline + 1, (yycolumn - yylength()) + 1)));
}

/* ================================= -- Comentario de bloque mal cerrado ================================= -- */
"/*"([^*]|\*+[^*/])* {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.ERROR, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    guardarToken(token);
}

/* ================================================== -- Operadores simples -- ====================================================== */
"+" {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.OPERADOR, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    token.setTipo2(TokenType.MAS);
    guardarToken(token);
}
"-" {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.OPERADOR, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    token.setTipo2(TokenType.MENOS);
    guardarToken(token);
}
"*" {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.OPERADOR, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    token.setTipo2(TokenType.POR);
    guardarToken(token);
}
"/" {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.OPERADOR, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    token.setTipo2(TokenType.DIV);
    guardarToken(token);
}
"%" {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.OPERADOR, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    guardarToken(token);
}

/* ================================= -- Errores quue no capta por si solo -- =================================*/
{Identificador}{Puntuacion} {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.ERROR, yytext(), inicio, new Position(yyline + 1, yycolumn - yylength() + 1));
    guardarToken(token);
}

{Numero}{Identificador} {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.ERROR, yytext(), inicio, new Position(yyline + 1, yycolumn - yylength() + 1));
    guardarToken(token);
}
/* ================================= -- Igual y punto y coma (tu enum tiene IGUAL y PCOMA) -- =================================*/
"=" {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.OPERADOR, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    token.setTipo2(TokenType.IGUAL);
    guardarToken(token);
}
";" {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.PUNTUACION, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    token.setTipo2(TokenType.PCOMA);
    guardarToken(token);
}

"." {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.PUNTUACION, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    guardarToken(token);
}
"," {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.PUNTUACION, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    guardarToken(token);
}
":" {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.PUNTUACION, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    /* token.setTipo2(TokenType.DOSPUNTOS); -- enum no lo tiene */
    guardarToken(token);
}

/* ================================= -- Agrupación (paréntesis, llaves, corchetes,estas coasa <>) -- =================================*/
{Agrupacion} {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.AGRUPACION, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    guardarToken(token);
}

/* ================================= -- Cadenas entre comillas (no permiten salto de línea dentro) -- ================================= */

\"([^\"\n\r])*[\n\r] {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.ERROR, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    guardarToken(token);
}

\"([^\"\n\r])*\" {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.CADENA, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    guardarToken(token);
}

/* ================================= -- Comentarios de línea (los generamos como token para coloreo) ================================= -- */
{LineCommentStart}[^\r\n]* {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.COMENTARIO_LINEA, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    guardarToken(token);
    /* el salto de línea será consumido por la regla de LineTerminator si aparece */
}

/* ================================= -- Número con punto pero sin decimales (error tipo 12.) -- ================================= */
{Numero}"." {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.ERROR, yytext().trim(), inicio,
        new Position(yyline+1, (yycolumn - yylength())+1 ));
    guardarToken(token);
}


/* ================================= -- Palabras reservadas  -- ================================= */
{ESCRIBIR} {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.PALABRAS_RESERVADAS, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    token.setTipo2(TokenType.ESCRIBIR);
    guardarToken(token);
}

{DEFINIR} {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.PALABRAS_RESERVADAS, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    token.setTipo2(TokenType.DEFINIR);
    guardarToken(token);
}

{COMO} {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.PALABRAS_RESERVADAS, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    token.setTipo2(TokenType.COMO);
    guardarToken(token);
}
/* ================================= otras reservadas generales: las marcamos como PALABRAS_RESERVADAS  ================================= */
{SI} {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.PALABRAS_RESERVADAS, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    guardarToken(token);
}

{ENTONCES} {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.PALABRAS_RESERVADAS, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    guardarToken(token);
}

{ENTERO} {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.PALABRAS_RESERVADAS, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    token.setTipo2(TokenType.ENTERO);
    guardarToken(token);
}
{NUMERO} {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.PALABRAS_RESERVADAS, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    token.setTipo2(TokenType.NUM); //????????????????????
    guardarToken(token);
}
{CADENA_WORD} {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.PALABRAS_RESERVADAS, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    token.setTipo2(TokenType.CAD); //????????????????????????
    guardarToken(token);
}

/* ================================= -- Identificadores (después de las reservadas) -- ================================= */
{Identificador} {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.IDENTIFICADOR, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    guardarToken(token);
}

/* ================================= -- Números y decimales  -- ================================= */
{Decimal} {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.DECIMAL, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    guardarToken(token);
}
{Numero} {
    int inicio = (int)(yychar);
    Token token = new Token(TokenType.NUMERO, yytext(), inicio, new Position(yyline+1, (yycolumn - yylength())+1 ));
    guardarToken(token);
}

/* ================================= -- Saltos de línea y espacios manejado -- ================================= */
{LineTerminator} {
    /* ignoramos, pero JFlex actualiza yyline/yycolumn por la opción %line %column */
}
{WhiteSpace} {
    /* ignoramos espacios y tabs */
}

/* -- EOF -- */
<<EOF>> {
/*
    int inicio = (int)yychar;
    Token token = new Token(TokenType.EOF, "EOF", inicio, new Position(yyline+1, yycolumn+1));
    guardarToken(token);
*/
    return null;
}


/* -- Cualquier otro carácter: error léxico-- */
. {
    int inicio = (int)(yychar);
    String lex = yytext();
    String descripcion = "Símbolo no reconocido: '" + lex + "'";
    Token token = new Token(TokenType.ERROR, yytext(), inicio, new Position(yyline + 1, yycolumn - yylength() + 1));
    guardarToken(token);
}

