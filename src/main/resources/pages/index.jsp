<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>
            Поисковик
        </title>
        <script>
            function load(){
                const urlParams = new URLSearchParams(window.location.search);
                search = urlParams.get('search');
                document.getElementById('search').value = search;
            }
            function getSearchByWord(){
                search = document.getElementById('search').value;
                location.href='http://localhost:8081/KorpSystems?search=' + search
            }

            function download(id, name){
                fetch('http://localhost:8081/KorpSystems/download?id=' + id)
                  .then((response) => response.blob())
                  .then(blob => URL.createObjectURL(blob))
                  .then((href) => {
                    const a = document.createElement("a")
                    document.body.appendChild(a)
                    a.style = "display: none"
                    a.href = href
                    a.download = name
                    a.click()
                  });
            }
        </script>
    </head>
    <body onload="load()">
        <input type="text" id="search"> <button onclick="getSearchByWord()">Поиск</button>
        <br>
        <c:if test="${not empty result}">
            <table>
                <tr>
                    <th>Имя файла</th>
                    <th>Ссылка</th>
                    <th>Релевантность</th>
                </tr>
                <c:forEach items="${result}" var="record">
                   <tr>
                       <td> ${record.appendixName}</td>
                       <td> <button onclick="download(${record.appendixId}, '${record.appendixName}')">Скачать</button> </td>
                       <td> ${record.relevance} </td>
                   </tr>
                </c:forEach>
            </table>
        </c:if>
    </body>
</html>