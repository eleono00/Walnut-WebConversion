<!DOCTYPE html>
<html>
<head>
    <title>Pagina dinamica</title>
</head>
<body>
    <h1>Benvenuto, <%= request.getAttribute("nome") %>!</h1>
    <p>La tua età è <%= request.getAttribute("età") %> anni.</p>
</body>
</html>

