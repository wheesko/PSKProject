import React, { useState } from 'react';

import { Calendar, Modal, Button, Typography, Tag, Divider } from 'antd';
import * as moment from 'moment';

import './CalendarStyles.css';
import { DAY_EVENT_LIST_EMPTY, MY_CALENDAR } from '../../../Constants';
import EventForm from './EventForm';
import { LearningEvent } from '../../../models/learningEvent';
import { learningEvents } from '../../../tools/mockData';

const { Title } = Typography;
const CalendarView: React.FunctionComponent<{}> = () => {
	// onSelect will display a modal to add event or open specific day
	const [modalVisibility, setModalVisibility] = useState<boolean>(false);
	const [isFormVisible, setIsFormVisible] = useState<boolean>(false);
	const [selectedDate, setSelectedDate] = useState<moment.Moment | undefined>(
		undefined
	);
	// status type is our example entity
	const [modalListData, setModalListData] = useState<LearningEvent[]>([]);
	const handleOnOk = (): void => {
		setModalVisibility(false);
		setIsFormVisible(false);
	};
	const handleOnCancel = (): void => {
		setModalVisibility(false);
		setIsFormVisible(false);
	};
	const handleSelect = (date?: moment.Moment | undefined): void => {
		console.log(date);
		setSelectedDate(date);
		setModalVisibility(true);
		if (date !== undefined) setModalListData(getListData(date));
	};
	// TODO: move DateCellRenderer into its own module when data fetching is implemented
	const DateCellRenderer = (value: moment.Moment): React.ReactNode => {
		const listData = getListData(value);

		return (
			<ul className="events" onClick={() => {
				console.log(value);
			}}>
				{listData.map(item => (
					<li key={item.id}>
						<Tag color={item.learningTopic!.color}>{item.name}</Tag>
					</li>
				))}
			</ul>
		);
	};

	return (
		<div>
			<Title level={2} className={'myCalendarTitle'}>{MY_CALENDAR}</Title>
			<Calendar
				dateCellRender={DateCellRenderer}
				monthCellRender={monthCellRender}
				onSelect={handleSelect}
			/>
			<Modal
				title={
					selectedDate === undefined
						? null
						: `${selectedDate.get('year')}-${selectedDate.get(
							'month'
						)}-${selectedDate.get('date')}`
				}
				visible={modalVisibility}
				onOk={handleOnOk}
				onCancel={handleOnCancel}
			>
				{modalListData.length === 0
					? null
					: modalListData.map(item => (
						<>
							<li key={item.id}>
								<Tag color={item.learningTopic?.color}>{item.name}</Tag>
								<p>{item.description}</p>
							</li>
							<Divider/>
						</>
					))}
				{selectedDate === undefined ? null : getListData(selectedDate).length === 0 ? (
					<p>{DAY_EVENT_LIST_EMPTY}</p>
				) : null}
				{isFormVisible ? null : (
					<Button
						className={'ant-btn ant-btn-primary'}
						onClick={() => {
							setIsFormVisible(true);
						}}
					>
						Add event
					</Button>
				)}
				{isFormVisible ? <EventForm/> : null}
			</Modal>
		</div>
	);
};
const getMonthData = (value: moment.Moment) => {
	if (value.month() === 8) {
		return 1394;
	}
};

const monthCellRender = (value: moment.Moment): React.ReactNode => {
	const num = getMonthData(value);

	return num ? (
		<div className="notes-month">
			<section>{num}</section>
			<span>Backlog number</span>
		</div>
	) : null;
};
// this method will be changed into a get request
  
const getListData = (value: moment.Moment) => {
	// fetch by year, month, day (or any arguments based on backend requirements)
	// console.log(value.year(), value.month(), value.date())

	// example data:
	let listData: LearningEvent[] = [];


	switch (value.date()) {
	case 8:
		listData = learningEvents.slice(0, learningEvents.length);
		break;
	case 10:
		listData = learningEvents.slice(0, 2);
		break;
	case 15:
		listData = learningEvents.slice(0, 1);
		break;
	default:
	}
	return listData;
};

// responsible for displaying items in each cell
const dateCellRender = (value: moment.Moment): React.ReactNode => {
	// value is the specific day
	// get data from backend about each day
	const listData = getListData(value);

	// and display
	return (
		<ul className='events'>
			{listData.map(item => (
				<li key={item.content}>
					<Badge status={item.type} text={item.content} />
				</li>
			))}
		</ul>
	);
};

const getMonthData = (value: moment.Moment) => {
	if (value.month() === 8) {
		return 1394;
	}
};

const monthCellRender = (value: moment.Moment) => {
	const num = getMonthData(value);

	return num ? (
		<div className='notes-month'>
			<section>{num}</section>
			<span>Backlog number</span>
		</div>
	) : null;
};
export default CalendarView;
