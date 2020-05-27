import React, { useEffect, useState } from 'react';

import { Button, Calendar, Divider, Modal, Spin, Tag, Tooltip, Typography } from 'antd';
import moment from 'moment';

import { DAY_EVENT_LIST_EMPTY, MY_CALENDAR } from '../../../constants/otherConstants';
import { EventForm } from './form/EventForm';
import { LearningEvent } from '../../../models/learningEvent';
import calendarService from '../../../api/calendar-service';

import './CalendarStyles.css';
import notificationService, { NotificationType } from '../../../service/notification-service';
import { PlusOutlined } from '@ant-design/icons';
import { NewEventForm } from './form/AddEventForm';
import { EditEventForm } from './form/EditEventForm';

const { Title } = Typography;

const CalendarView: React.FunctionComponent<{}> = () => {
	// onSelect will display a modal to add event or open specific day
	const [modalVisibility, setModalVisibility] = useState<boolean>(false);
	const [isFormVisible, setIsFormVisible] = useState<boolean>(false);
	const [selectedDate, setSelectedDate] = useState<moment.Moment | undefined>(
		undefined
	);
	const [addEventModalVisible, setAddEventModalVisibility] = useState<boolean>(false);
	const [editEventFormVisible, setEditEventVisibility] = useState<boolean>(false);

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
		setEditEventVisibility(false);
	}

	function handleOnCancel (): void {
		setModalVisibility(false);
		setIsFormVisible(false);
		setEditEventVisibility(false);
	}

	function handleOnAddEventOk(): void {
		setAddEventModalVisibility(false);
	}

	function handleOnAddEventCancel(): void {
		setAddEventModalVisibility(false);
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
							<Tag color={'red'}>{item.topic.name}</Tag>
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
			{renderDateCellModal()}
			<div className="floating-plus-button">
				<Tooltip placement="top" title={'Add new learning day'}>
					<Button
						size="large"
						shape="circle"
						className="add-learning-day-button"
						onClick={() => setAddEventModalVisibility(true)}
					>
						<PlusOutlined className="add-learning-day-button"/>
					</Button>
				</Tooltip>
			</div>
			{renderAddEventModal()}
		</div>
	);

	function onCreateDay() {
		loadData(selectedDate === undefined ? moment() : selectedDate).then(learningEvents => {
			setCalendarListData(learningEvents);
			setModalVisibility(false);
		});
	}

	function onUpdateDay() {
		loadData(selectedDate === undefined ? moment() : selectedDate).then(learningEvents => {
			setCalendarListData(learningEvents);
			setModalVisibility(false);
			setEditEventVisibility(false);
		});
	}

	//TODO: Refactor to component
	function renderDateCellModal(): React.ReactNode {
		return (
			<Modal
				title={
					selectedDate === undefined
						? null
						: selectedDate.format('yyyy-MM-DD')
				}
				visible={modalVisibility}
				onOk={handleOnOk}
				onCancel={handleOnCancel}
				footer={null}
			>
				{!editEventFormVisible && renderModalLearningDayData()}
				{
					selectedDate === undefined ? null : calendarListData.length === 0 ? (
						<p>{DAY_EVENT_LIST_EMPTY}</p>
					) : null
				}
				{renderAddEventButton()}
				{
					isFormVisible
						?
						<EventForm
							selectedDate={selectedDate}
							onCreateDay={onCreateDay}
					  	/>
						: null
				}
				{
					editEventFormVisible
						? <EditEventForm
							initialValues={getInitialEditValues()}
							onCreateDay={onUpdateDay}
						/>
						: null
				}
				{renderEditFormButton()}
			</Modal>
		);
	}

	function openEditForm() {
		setEditEventVisibility(true);
	}

	function closeEditForm() {
		setEditEventVisibility(false);
	}

	function getInitialEditValues() {
		return modalListData[0];
	}
	
	function renderEditFormButton(): React.ReactNode {
		return modalListData.length === 0
			? null
			: editEventFormVisible
				? <Button onClick={closeEditForm} type="primary">
					Cancel
				</Button>
		  		: <Button onClick={openEditForm} type="primary">
					Edit event
				</Button>;
	}

 	function renderAddEventButton(): React.ReactNode {
		return (isFormVisible || modalListData.length !== 0)
			? null
			: (
				<Button
					className={'ant-btn ant-btn-primary'}
					onClick={() => {
						setIsFormVisible(true);
					}}
					disabled={modalListData.length !== 0}
				>
					Add event
				</Button>
			);
	}
	
	function renderModalLearningDayData(): React.ReactNode {
		return modalListData.length === 0
			? null
			: modalListData.map(item => (
				<>
					<li key={item.id}>
						<Typography.Paragraph>Topic name: {item.topic.name}</Typography.Paragraph>
						<Typography.Paragraph>Description: {item.topic.description}</Typography.Paragraph>
						<Typography.Paragraph>Comments: {item.comment}</Typography.Paragraph>
					</li>
					<Divider/>
				</>
			));
	}
	
	function renderAddEventModal(): React.ReactNode {
		return (
			<Modal
				visible={addEventModalVisible}
				onCancel={handleOnAddEventCancel}
				onOk={handleOnAddEventOk}
				footer={null}
			>
				<NewEventForm onCreateDay={onCreateDay}/>
			</Modal>
		);
	}
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
