/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.exoplayer2.source.rtp.extractor;

import android.util.SparseArray;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.rtp.format.RtpVideoPayload;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.ParsableNalUnitBitArray;
import com.google.android.exoplayer2.util.TrackIdGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class RtpMPEGPayloadReader implements RtpPayloadReader {
    public static MJPEGConsumer MJPEG_CONSUMER;
    public interface MJPEGConsumer {
        void consume(ParsableByteArray data);
    }

    private TrackOutput output;

    public RtpMPEGPayloadReader() {
    }

    @Override
    public void seek() {
        // can't seek ipcam stream
    }

    @Override
    public void createTracks(ExtractorOutput extractorOutput, TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();

        int trackId = trackIdGenerator.getTrackId();
        String formatId = trackIdGenerator.getFormatId();

        output = extractorOutput.track(trackId, C.TRACK_TYPE_VIDEO);
    }

    @Override
    public boolean packetStarted(long sampleTimeStamp, boolean nalUnitCompleteIndicator,
                              int sequenceNumber) {
        return true;
    }

    @Override
    public void consume(ParsableByteArray packet) throws ParserException {
        if (MJPEG_CONSUMER != null) MJPEG_CONSUMER.consume(packet);
    }
}
