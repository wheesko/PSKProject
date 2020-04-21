import { createStore, combineReducers, applyMiddleware } from 'redux';
import thunkMiddleware from 'redux-thunk';
import { composeWithDevTools } from 'redux-devtools-extension';

import { userReducer } from './user/reducers';
import { UserState } from './user/types';

export interface RootState {
	user: UserState;
}

// with more reducers, we will combine them together into one store
const rootReducer = combineReducers({
	user: userReducer,
});

export type AppState = ReturnType<typeof rootReducer>;

export default function configureStore() {
	const middlewares = [thunkMiddleware];
	const middleWareEnhancer = applyMiddleware(...middlewares);

	const store = createStore(
		rootReducer,
		composeWithDevTools(middleWareEnhancer)
	);

	return store;
}
