import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { App } from './App';
import * as serviceWorker from './serviceWorker';
import { Router } from 'react-router-dom';
import history from './history';
import configureStore from './redux';
import 'antd/dist/antd.css';
import './index.css';
import { PersistGate } from 'redux-persist/integration/react';

const {
	store,
	persistedStore
} = configureStore();


ReactDOM.render(
	<Provider store={store}>
		<PersistGate persistor={persistedStore}>
			<Router history={history}>
				<App/>
			</Router>
		</PersistGate>
	</Provider>,
	document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
