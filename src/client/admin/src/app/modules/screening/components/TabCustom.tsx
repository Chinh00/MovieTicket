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
import {Box} from "@mui/material";
import AddScreeningModel from "@/app/modules/screening/components/AddScreeningModel.tsx";
import {AppConfig} from "@/core/config/AppConfig.ts";
import utc from "dayjs/plugin/utc"

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
        ],
        sortBy: ["startDateDesc"]
    }, !!props.roomId && !!_date)
    //
    {dayjs.extend(utc)}
    
    return <TabPanel value={props?.value}>
        <AddScreeningModel retry={refetch} roomId={props?.roomId} />
        
        <Timeline position={"alternate"} >
            {!!data &&  data?.data?.data?.items?.map((value, index) => {
                console.log(value?.startDate)
                return <div key={index}>
                    <TimelineItem>
                        <TimelineOppositeContent color="text.secondary">
                            {value?.startDate.toString()}
                        </TimelineOppositeContent>
                        <TimelineSeparator>
                            <TimelineDot />
                            <TimelineConnector />
                        </TimelineSeparator>
                        <TimelineContent>
                            <Box display={"flex"} gap={5}>
                                <img src={`${AppConfig.BASE_URL}/admin-api/image${value?.movie?.avatar}`} width={150} height={200}/> 
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