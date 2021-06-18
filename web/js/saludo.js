function mostraSaludo()
{
    fecha = new Date();
    hora = fecha.getHours();
    if (hora >= 0 && hora < 12)
    {
        texto = "Buenos Dias";
        imagen = "img/dia.jpg";
    }
    if (hora >= 12 && hora < 18)
    {
        texto = "Buenas Tardes";
        imagen = "img/tarde.jpg";
    }
    if (hora > 18 && hora < 24)
    {
        texto = "Buenas Noches";
        imagen = "img/noche.jpg";
    }
    Document.images["tiempo"].src = imagen;
    Document.getElementById('saludo').innerHTML = texto;
    Document.getElementById('saludo2').innerHTML = texto;
}
;


