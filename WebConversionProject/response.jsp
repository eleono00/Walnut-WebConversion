<!DOCTYPE html>
<html>
<head>
    <title>Pagina dinamica</title>
</head>
<body>
    <h1>Benvenuto, <%= request.getAttribute("nome") %>!</h1>
    <p>La tua et� � <%= request.getAttribute("et�") %> anni.</p>
</body>
</html>

