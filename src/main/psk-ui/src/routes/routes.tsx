import React from 'react';
import { Route, Switch } from 'react-router-dom';
import { LandingPage } from '../pages/private/landing';
import {
	KEY_CALENDAR,
	KEY_INFO,
	KEY_MEMBERS,
	KEY_MY_CALENDAR, KEY_NEW_TOPIC,
	KEY_PROFILE,
	KEY_TEAMS,
	KEY_TOPIC_TREE,
	KEY_TOPICS
} from '../Constants';
import { CalendarView } from '../pages/private/calendar-view';
import { ProfileView } from '../pages/private/profile-view';
import { TeamCalendarView } from '../pages/private/team-calendar-view';
import { TeamMembersView } from '../pages/private/team-members-view';
import { InfoView } from '../pages/private/info-view';
import { TopicTreeView } from '../pages/private/topic-tree-view';
import { NewTopicView } from '../pages/private/new-topic-view';

const Routes: React.FunctionComponent<{}> = () => {
	return (
		<Switch>
			<Route
				exact
				path="/"
				component={LandingPage}
			/>
			<Route exact path={`/${KEY_MY_CALENDAR}`} component={CalendarView}/>
			<Route
				exact
				path={`/${KEY_MY_CALENDAR}/:year/:month/:day`}/>
			<Route
				exact
				path={`/${KEY_PROFILE}`}
				component={ProfileView}
			/>
			<Route exact path={`/${KEY_TEAMS}/${KEY_CALENDAR}`} component={TeamCalendarView}/>

			<Route exact path={`/${KEY_TEAMS}/${KEY_MEMBERS}`} component={TeamMembersView}/>
			<Route exact path={`/${KEY_TEAMS}/${KEY_CALENDAR}`}/>
			<Route exact path={`/${KEY_TEAMS}/${KEY_INFO}`} component={InfoView}/>
			<Route exact path={`/${KEY_TOPICS}/${KEY_TOPIC_TREE}`} component={TopicTreeView}/>
			<Route exact path={`/${KEY_TOPICS}/${KEY_NEW_TOPIC}`} component={NewTopicView}/>
		</Switch>
	);
};

export { Routes };