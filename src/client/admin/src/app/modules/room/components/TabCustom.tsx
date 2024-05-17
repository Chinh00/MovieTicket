import {memo} from "react";
import dayjs from "dayjs";
import {
    TabPanel,
    TabPanelProps,
    Timeline, TimelineConnector, TimelineContent,
    TimelineDot,
    TimelineItem,
    TimelineOppositeContent,
    TimelineSeparator
} from "@mui/lab";
import {useGetScreenings} from "@/app/usecases/screening.usecase.ts";
import AddScreeningModel from "@/app/modules/room/components/AddScreeningModel.tsx";
import {Box} from "@mui/material";

export type TabCustomProps = {
    _date: dayjs.Dayjs,
    roomId: string
} & TabPanelProps





const TabCustom = ({_date, ...props}: TabCustomProps) => {
    const {data, refetch} = useGetScreenings({
        filters: [
            {
                fieldName: "RoomId",
                comparision: "==",
                fieldValue: props?.roomId
            },
            {
                fieldName: "StartDate",
                comparision: "<",
                fieldValue: `${_date?.startOf("day").add(1, "day").toISOString()}`
            },
            {
                fieldName: "StartDate",
                comparision: ">=",
                fieldValue: `${_date?.startOf("day").toISOString()}`
            }
        ]
    }, !!props.roomId && !!_date)
    //
    return <TabPanel value={props?.value}>
        <AddScreeningModel retry={refetch} roomId={props?.roomId} />
        {_date?.get("day")}
        
        <Timeline position={"alternate"} >
            {!!data &&  data?.data?.data?.items?.map((value, index) => {
                return <div key={index}>
                    <TimelineItem>
                        <TimelineOppositeContent color="text.secondary">
                            {dayjs(value?.startDate).format("DD/MM/YYYY HH:mm:ss")}
                        </TimelineOppositeContent>
                        <TimelineSeparator>
                            <TimelineDot />
                            <TimelineConnector />
                        </TimelineSeparator>
                        <TimelineContent>
                            <Box display={"flex"} gap={5}>
                                <img src={value?.movie?.avatar} width={150} height={200}/> 
                                <p className={"text-xl"}>{value?.movie?.name}</p>
                            </Box>
                        </TimelineContent>
                    </TimelineItem>
                    
                </div>
            })}
        </Timeline>
        
        
    </TabPanel>
}

export default memo(TabCustom)