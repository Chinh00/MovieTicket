import {Box, Tab} from "@mui/material";
import {
    TabContext, TabList, TabPanel,
    Timeline,
    TimelineConnector, TimelineContent,
    TimelineDot,
    TimelineItem,
    TimelineOppositeContent,
    TimelineSeparator
} from "@mui/lab";
import {useEffect, useState} from "react";
import dayjs from "dayjs";
import vi from 'dayjs/locale/vi'
import {dayOfWeek} from "@/infrastructure/utils/datetime.ts";
import {useParams} from "react-router-dom";
import TabCustom from "@/app/modules/screening/components/TabCustom.tsx";

type TabOptions = {
    id: number,
    value: dayjs.Dayjs
}


const RoomScreenings = () => {
    const [dates, setDates] = useState<TabOptions[]>([])
    const {id} = useParams()
    useEffect(() => {
        let _dates: TabOptions[] = [] 
        const currentDate = dayjs()
        for (let i = 0; i < 7; i++) {
            _dates.push({
                id: i,
                value: currentDate.add(i, "day")
            })
        }
        setDates(prevState => [...prevState, ..._dates])
    }, []);
    const [currentTab, setCurrentTab] = useState("0")
    
    
    return <Box className={"mt-10 p-10"}>
        
        
       

        <Box sx={{ width: '100%', typography: 'body1' }}>
            <TabContext value={currentTab}>
                <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                    <TabList variant={"fullWidth"} centered={true} onChange={(e, val) => setCurrentTab(val)} aria-label="lab API tabs example">
                        
                        {!!dates && dates?.map((value, index) => {
                            return <Tab key={value.id} value={`${value.id}`} label={
                                <div className={"flex flex-col justify-center items-center"}>
                                    <p className={" font-bold"}>{dayOfWeek[value?.value?.day()]}</p>
                                    <p>{value?.value?.format("DD/MM/YYYY")}</p>
                                </div>
                                
                            } />
                        })}
                    </TabList>
                </Box>
                {!!dates && <TabCustom roomId={id || ""} value={currentTab} _date={dates[Number.parseInt(currentTab)]?.value} />}           
            </TabContext>
        </Box>
    </Box>
}

export default RoomScreenings