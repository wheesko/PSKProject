# PSKProject
PSK komandinis projektas

# Requirements
  - Node.JS
  - JDK 11
  - ESlint
  
# Setup
Start frontend (skip npm install if not first time):
```
cd psk-ui
npm install
npm start
```

Start backend: 
```
gradle bootRun
```

Now if you click the button, you should see api calls being made to backend

Install ESlint:
- Go to preferences, ESLint plugin page and check the Enable plugin.
- Set the path to the nodejs interpreter bin file.
- Set eslintr.c path to psk-ui/.eslintrc.json
- Set the path to the eslint bin file. should point to <project path>node_modules/eslint/bin/eslint.js if you installed locally or /usr/local/bin/eslint if you installed globally.
    - For Windows: install eslint globally and point to the eslint cmd file like, e.g. C:\Users\<username>\AppData\Roaming\npm\eslint.cmd
- You can also set a path to a custom rules directory.

