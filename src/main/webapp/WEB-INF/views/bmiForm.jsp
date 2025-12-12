<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<h2>BMI Form</h2>
<form method="post" action="bmi">
    <label>Full name : </label><input name="name"></br>
    <label>Year Born (yyyy) : </label><input name="yob" type="number"></br>
    <label>Weight (kg): </label><input name="weight" type="number" step="0.1"></br>
    <label>Height (m) : </label><input name="height" type="number" step="0.01"></br>
    <button type="submit">Calculate BMI</button></br>
</form>
