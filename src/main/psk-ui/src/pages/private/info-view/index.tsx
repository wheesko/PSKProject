import React, { useState } from 'react';
import { Table, Typography } from 'antd';
import './InfoStyles.css';
import { TopicFilter } from '../../../models/topicFilter';
import { teams } from '../../../tools/mockData';

const { Title } = Typography;
const InfoView: React.FunctionComponent<{}> = () => {
	// set topic filters from api
	const [topicFilters, setTopicFilters] = useState<TopicFilter[]>([{ text: 'Java', value: 'java' }, {
		text: 'SQL',
		value: 'sql'
	}]);
	// handle selected topic filters from table
	const [selectedTopicFilters, setSelectedTopicFilters] = useState<TopicFilter[]>([]);
	const columns = [{
		title: 'Team',
		dataIndex: 'teamName',
		key: 'teamName',
	},
	{
		title: 'Manager',
		dataIndex: 'manager',
		key: 'manager',
	},
	{
		title: `Employees with ${selectedTopicFilters.map(tf => tf.text).join()} skills`,
		dataIndex: 'employeeAmount',
		key: 'employeeAmount',
		filters: topicFilters,
	},
	{
		title: `Employees planning to learn ${selectedTopicFilters.map(tf => tf.text).join()}`,
		dataIndex: 'planningAmount',
		key: 'planningAmount',
	}];

	return <>
		<Title level={4} className={'infoTitle'}>
			{/*TODO: maybe this title will be a reminder or a helper text in the future*/}
			Here you can view team data by learned topic
		</Title>
		<Table columns={columns} dataSource={teams}></Table>
	</>;
};

export default InfoView;