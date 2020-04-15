import React from 'react';

import { Calendar, Typography } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import { List, Avatar, Badge } from 'antd';
import './TeamCalendarStyles.css';
import { MY_TEAM_CALENDAR, N_LEARNING_EVENTS } from '../../../Constants';
import * as moment from 'moment';
import { Link } from 'react-router-dom';

const { Title } = Typography;
const TeamCalendarView: React.FunctionComponent<{}> = () => {
	const eventData = [
		{
			title: 'Java basics (10:00-12:00)',
		},
		{
			title: 'Advanced SQL (14:00-15:00)',
		},
		{
			title: 'Ethical Hacking (15:00-16:00)',
		},
		{
			title: 'Designing UIs (16:00-17:00)',
		},
	];
		// responsible for displaying items in each cell
	const dateCellRender = (value: moment.Moment): React.ReactNode => {
		// value is the specific day
		// get data from backend about each day
		return getListData(value);
	};
	const getListData = (value: moment.Moment) => {
		const DataList = () => {
			return <>
				<Badge count={N_LEARNING_EVENTS(eventData.length)} className="site-badge-count-4"
				/>
				<List
					itemLayout="horizontal"
					dataSource={eventData}
					renderItem={item => (
						<List.Item className={'employee-learning-event-item'}>
							<List.Item.Meta
								avatar={Math.floor(Math.random() * 10) > 5 ?
									<Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"/> :
									<Avatar style={{ backgroundColor: '#87d068' }} icon={<UserOutlined/>}/>}
								title={<Link to={`/event/${item.title}`}>{item.title}</Link>}
							/>
						</List.Item>
					)}
				/>
			</>;

		};

		switch (value.date()) {
		case 8:
			return <DataList/>;
		case 10:
			return <DataList/>;
		case 15:
			return <DataList/>;
		default:
			return null;
		}
	};

	return <>
		<Title level={2} className={'teamCalendarTitle'}>{MY_TEAM_CALENDAR}</Title>
		<Calendar dateCellRender={dateCellRender}/>
	</>;
}

;
export default TeamCalendarView;