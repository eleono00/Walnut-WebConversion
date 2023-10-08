<%-- 
    Document   : dettagli
    Created on : May 19, 2023, 4:48:01 PM
    Author     : asuseleonora
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   
<head>
    <title>Dettagli</title>
     <script src="https://cdnjs.cloudflare.com/ajax/libs/viz.js/2.1.2/viz.js"></script>
     <script src="https://cdnjs.cloudflare.com/ajax/libs/viz.js/2.1.2/full.render.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.7/MathJax.js?config=TeX-AMS_HTML"></script>
   <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
        <link rel="stylesheet" href="newcss.css" />
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="#"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">Dettagli ${elemento}</font></font></a>
   
  </div>
</nav> 
<style>
            .testo{
                margin-bottom: 2rem;
                margin-top: 2rem;
                margin-left: 1rem;
                margin-right: 1rem;
                display: flex;
                justify-content: center;
                align-items: center;
            }
            
        </style>
<h5><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">Latex traduzione</font></font></h4>


<div class="testo" id="math-expression"></div>

<script>function translatePredicateToLatex(predicate) {
  // Trova l'indice del carattere ":" nel predicato
  var colonIndex = predicate.indexOf(':');

  // Dividi il predicato in due parti: prima e dopo il carattere ":"
  var prefix = predicate.substring(0, colonIndex + 1);
  var suffix = predicate.substring(colonIndex + 1);

  // Rimuovi eventuali spazi seguiti da una stringa che inizia con "?"
  suffix = suffix.replace(/:\s*\?.*/, '');

  // Applica le traduzioni solo alla parte dopo il carattere ":"
  suffix = suffix.replace(/\*/g, "\\cdot ");
  suffix = suffix.replace(/\//g, "\\div ");
  suffix = suffix.replace(/\+/g, "+");
  suffix = suffix.replace(/</g, "\\lt ");
  suffix = suffix.replace(/>/g, "\\gt ");
  suffix = suffix.replace(/=>/g, "\\Rightarrow ");
  suffix = suffix.replace(/-/g, "-");
  suffix = suffix.replace(/=/g, "=");
  suffix = suffix.replace(/!=/g, "\\neq ");
  suffix = suffix.replace(/<=>/g, "\\Leftrightarrow ");
  suffix = suffix.replace(/<=/g, "\\leq ");
  suffix = suffix.replace(/>=/g, "\\geq ");
  suffix = suffix.replace(/&/g, "\\wedge ");
  suffix = suffix.replace(/\|/g, "\\vee ");
  suffix = suffix.replace(/\^/g, "\\oplus ");
  suffix = suffix.replace(/([^\\])_/g, '$1\\_');  // Evita la traduzione del carattere "_"

  // Quantificatori
  suffix = suffix.replace(/E/g, "\\exists ");
  suffix = suffix.replace(/A/g, "\\forall ");

  // Indicizzazione
  suffix = suffix.replace(/\[/g, "_{");
  suffix = suffix.replace(/\]/g, "}");

  // Complemento e inverso
  suffix = suffix.replace(/~/g, "\\sim ");
  suffix = suffix.replace(/'/g, "^{-1}");

  // Aggiungere parentesi
  suffix = "(" + suffix + ")";

  // Ricompone il predicato completo
  var translatedPredicate = prefix + suffix;

  return translatedPredicate;
}





  function convertToMathJax() {
    var mathExpression = document.getElementById("math-expression");

    // Ottieni l'attributo "predicato" dalla richiesta
    var predicateInput = '<%= request.getAttribute("predicato") %>';
    var predicate = predicateInput.trim();

    var latexPredicate = translatePredicateToLatex(predicate);

    mathExpression.innerHTML = "\\(" + latexPredicate + "\\)";
    MathJax.Hub.Queue(["Typeset", MathJax.Hub, mathExpression]);
  }

  convertToMathJax();
</script>

<style>
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
        }
        
        #graphContainer {
            border: 1px solid #ccc;
            padding: 10px;
            width: 100%;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        #graphContainer img {
            max-width: 100%;
            max-height: 100%;
        }
    </style>
   <h5><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">Automa</font></font></h4>


    <div id="graphContainer"></div>
    <script>
        const gvCode = `<%= request.getAttribute("fileContent") %>`;
        
        const container = document.getElementById('graphContainer');
        
        const viz = new Viz();
        
        viz.renderImageElement(gvCode)
            .then(element => {
                container.appendChild(element);
            })
            .catch(error => {
                console.error('Errore nella generazione del grafico:', error);
            });
    </script>
</body>   


</body>
</html>