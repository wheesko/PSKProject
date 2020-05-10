import { createStore, combineReducers, applyMiddleware, Store } from 'redux';
import thunkMiddleware from 'redux-thunk';

import { userReducer } from './user/reducers';
import { UserState } from './user/types';
import {
	createStateSyncMiddleware,
	initStateWithPrevTab,
	withReduxStateSync
} from 'redux-state-sync';
import { composeWithDevTools } from 'redux-devtools-extension';
import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';

export interface RootState {
	user: UserState;
}

// with more reducers, we will combine them together into one store
const combinedReducers: any = combineReducers({ user: userReducer });
const rootReducer = withReduxStateSync(combinedReducers);

const persistedReducers: any = persistReducer({ key: 'root', storage }, rootReducer);


export type AppState = ReturnType<typeof rootReducer>;

export default function configureStore() {
	const middlewares: any = [thunkMiddleware, createStateSyncMiddleware({})];
	const middleWareEnhancer = applyMiddleware(...middlewares);

	const store: Store = createStore(
		persistedReducers,
		{},
		composeWithDevTools(middleWareEnhancer)
	);

	const persistedStore = persistStore(store);

	initStateWithPrevTab(store);

	return { store, persistedStore };
}
