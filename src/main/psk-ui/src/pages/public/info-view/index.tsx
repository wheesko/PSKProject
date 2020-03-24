import React, {useState} from 'react';
import {Table, Typography, Dropdown, Button, Menu,} from 'antd';
import {FilterOutlined} from '@ant-design/icons';
import './InfoStyles.css'
import {TopicFilter} from "../../../models/topic-filter-model";

const {Title} = Typography;
const InfoView: React.FunctionComponent<{}> = () => {
	// set topic filters from api
	const [topicFilters, setTopicFilters] = useState<TopicFilter[]>([{text: 'Java', value: 'java'}, {
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
	const dataSource = [{
		teamName: 'Alpha',
		manager: 'Fictional Manager1',
		employeeAmount: 3,
		planningAmount: 4,
	},
		{
			teamName: 'Beta',
			manager: 'Fictional Manager1',
			employeeAmount: 5,
			planningAmount: 7,
		}];
	return <>
		<Title level={4} className={"infoTitle"}>

			{/*WIP: maybe this title will be a reminder or a helper text in the future*/}
			Here you can view team data by learned topic
		</Title>
		<Table columns={columns} dataSource={dataSource}></Table>
	</>
};

export default InfoView;