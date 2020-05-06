import React from 'react';
import {Button, Card, Timeline, Typography} from 'antd';
// this is for "My Profile"
// But may(?) be used for viewing individual profiles of employees
import './ProfileStyles.css';

// conditionally render editable fields = if you're the account "holder", you should be access editing the profile.
const { Title } = Typography;

const ProfileView: React.FunctionComponent<{}> = () => {

	return <>
		<p>Some info about users profile will be displayed here</p>
		<Card size="small" className="learning-timeline-card">
			<Title level={4}>Learning event history</Title>
			<Timeline mode="left">
				<Timeline.Item label="2020-02-01">Started learning Java fundamentals</Timeline.Item>
				<Timeline.Item label="2020-02-05 (13:00-17:00)">Java fundamentals</Timeline.Item>
				<Timeline.Item label="2020-02-20 (09:00-12:00)">Java fundamentals</Timeline.Item>
				<Timeline.Item label="2020-02-24 (13:00-17:00)">Advanced SQL statements</Timeline.Item>
			</Timeline>
		</Card>
	</>;
};

export { ProfileView };