import React, { useEffect, useState } from 'react';

import { Button, Calendar, Divider, Modal, Spin, Tag, Typography } from 'antd';
import moment from 'moment';

import { DAY_EVENT_LIST_EMPTY, MY_CALENDAR } from '../../../constants/otherConstants';
import { EventForm } from './EventForm';
import { LearningEvent } from '../../../models/learningEvent';
import calendarService from '../../../api/calendar-service';

import './CalendarStyles.css';
import notificationService, { NotificationType } from '../../../service/notification-service';

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
	const [calendarListData, setCalendarListData] = useState<LearningEvent[]>([]);
	const [isLoading, setLoading] = useState<boolean>(false);

	//component did mount
	useEffect(() => {
		loadData(moment()).then(learningEvents =>
			setCalendarListData(learningEvents)
		);
	}, []);

	function handleOnOk(): void {
		setModalVisibility(false);
		setIsFormVisible(false);
	}

	function handleOnCancel (): void {
		setModalVisibility(false);
		setIsFormVisible(false);
	}

	function handleSelect(date?: moment.Moment | undefined): void {
		setSelectedDate(date);
		setModalVisibility(true);
		if (date !== undefined) setModalListData(getListData(date));
	}
	// TODO: move DateCellRenderer into its own module when data fetching is implemented
	function DateCellRenderer(value: moment.Moment): React.ReactNode {
		//TODO: Rethink colors of calendar cells
		return (
			<div className="events-cell-wrapper" onClick={() => handleSelect(value)}>
				<ul className="events">
					{getListData(value).map(item => (
						<li key={item.id}>
							<Tag color={'red'}>{item.name}</Tag>
						</li>
					))}
				</ul>
			</div>
		);
	}

	function getListData(value: moment.Moment): LearningEvent[] {
		return calendarListData.filter(learningEvent =>
			moment(value.toDate()).isSame(learningEvent.dateTimeAt, 'date'));
	}

	function loadData(value: moment.Moment): Promise<LearningEvent[]> {
		// fetch by year, month, day (or any arguments based on backend requirements)
		// console.log(value.year(), value.month(), value.date())

		// example data:
		setLoading(true);
		return calendarService.getMonthLearningDays({
			selectedYear: value.format('Y'),
			selectedMonth: value.format('M')
		}).then((response) => {
			setLoading(false);
			return response;
		}).catch((error) => {
			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: 'Failed to load events',
				description: error.toString()
			});
			setLoading(false);
			return [];
		});
	}

	function getMonthEvents(value: moment.Moment) {
		loadData(value).then((learningEvents) => {
			setCalendarListData(learningEvents);
		});
	}

	// TODO: This return is extremely long - refactor it to smaller components or render functions
	return (
		<div>
			<Title level={2} className={'myCalendarTitle'}>{MY_CALENDAR}</Title>
			<Spin spinning={isLoading} size="large">
				<Calendar
					dateCellRender={DateCellRenderer}
					monthCellRender={renderMonthCell}
					onChange={getMonthEvents}
				/>
			</Spin>
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
				{selectedDate === undefined ? null : calendarListData.length === 0 ? (
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
// this method will be changed into a get request

function getMonthData (value: moment.Moment): number | null {
	if (value.month() === 8) {
		return 1394;
	}
	return null;
}

function renderMonthCell(value: moment.Moment): React.ReactNode {
	const num = getMonthData(value);

	return num ? (
		<div className="notes-month">
			<section>{num}</section>
			<span>Backlog number</span>
		</div>
	) : null;
}

export { CalendarView };
