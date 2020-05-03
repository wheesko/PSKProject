# PSKProject
PSK komandinis projektas

# Requirements
  - Node.JS
  - JDK 11
  - ESlint
  
# Setup
Start frontend (skip npm install if not first time):
```
cd src/main/psk-ui
npm install
npm start
```

Start backend: 
```
mvnw spring-boot:run
```

Now if you click the button, you should see api calls being made to backend

Install ESlint:
- Go to preferences, ESLint plugin page and check the Enable plugin.
- Set the path to the nodejs interpreter bin file.
- Set eslintr.c path to psk-ui/.eslintrc.json
- Set the path to the eslint bin file. should point to <project path>node_modules/eslint/bin/eslint.js if you installed locally or /usr/local/bin/eslint if you installed globally.
    - For Windows: install eslint globally and point to the eslint cmd file like, e.g. C:\Users\<username>\AppData\Roaming\npm\eslint.cmd
- You can also set a path to a custom rules directory.

# Connecting to DB

After you've launched the application, you should be able to open h2 database via url
```
http://localhost:8080/h2-console
```

In this window your JDBC URL field should look like the one in the field `spring.datasource.url`

```
src/main/resources/application.properties
```

Once you connect, you should be able to see entity tables

You can test out the api calls via postman, `PUT` and `GET` methods should be working properly.

# Swagger UI

You can access swagger UI and see implemented endpoints at
```
localhost:8080/swagger-ui.html
```
Click authorize and insert JWT in format (get it from postman or frontend)
```
Bearer <YOUR_JWT_VALUE>
```
You should now be able to call them, provided your JWT was correct and not expired